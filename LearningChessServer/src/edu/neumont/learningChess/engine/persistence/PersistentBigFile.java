package edu.neumont.learningChess.engine.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersistentBigFile {
	private static final String METADATA_EXTENSION = ".metadata";
	private Map<Long, FileChannel> fileChannels = new HashMap<Long, FileChannel>();
	private long fileIndex = 0;
	private long fileOffset = 0;
	private final String baseFileName;
	private PersistentBigFileMetaData metaData;
	private static final long MAX_FILE_SIZE = 500000000;

	private PersistentBigFile(String fileName) {
		this.baseFileName = fileName;
		metaData = readMetaData(getMetaDataFileName(fileName));
	}

	public static void create(String fileName) {
		if (fileName.isEmpty() || fileName == null) {
			throw new RuntimeException("Invalid file name: " + fileName + ".");
		}
		File file = new File(getMetaDataFileName(fileName));
		if (file.exists()) {
			throw new RuntimeException("Cannot create existing file - " + fileName + ".");
		}
		String metaDataFileName = getMetaDataFileName(fileName);
		writeMetaData(metaDataFileName, new PersistentBigFileMetaData(MAX_FILE_SIZE, 0));

	}
	private static String getMetaDataFileName(String fileName) {
		return fileName + METADATA_EXTENSION;
	}
	private static void writeMetaData(String metaDataFileName, PersistentBigFileMetaData metaData) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(new File(metaDataFileName));
			fileOutputStream.write(metaData.serialize());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File " + metaDataFileName + "not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Exception while writing metadata", e);
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("IO Exception while closing", e);
			}
		}
	}

	private static PersistentBigFileMetaData readMetaData(String metaDataFileName) {
		FileInputStream fileInputStream = null;
		PersistentBigFileMetaData metaData = null;
		try {
			fileInputStream = new FileInputStream(new File(metaDataFileName));
			byte[] buffer = new byte[PersistentBigFileMetaData.getMetaDataSize()];
			fileInputStream.read(buffer);
			metaData = new PersistentBigFileMetaData(buffer);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File " + metaDataFileName + "not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Exception while reading metadata", e);
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Exception while closing", e);
			}
		}
		return metaData;
	}

	public static PersistentBigFile open(String fileName) {
		PersistentBigFile persistentBigFile = new PersistentBigFile(fileName);
		return persistentBigFile;
	}

	public static void delete(String fileName) {
		File metaDataFile = new File(getMetaDataFileName(fileName));
		if (metaDataFile.exists()) {
			if (!metaDataFile.delete()) {
				throw new RuntimeException("The file, with file name " + fileName + ", failed to be deleted. (The stream might not be closed)");
			}
		} else {
			throw new RuntimeException("There is no file, with file name " + fileName + ", to delete.");
		}
		String regExp = fileName + "_[\\d]+";
		Pattern pattern = Pattern.compile(regExp);
		File file = new File(fileName).getAbsoluteFile().getParentFile();
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File child : children) {
				String childFileName = child.getAbsolutePath();
				Matcher matcher = pattern.matcher(childFileName.substring(childFileName.lastIndexOf(File.separator)));
				if (matcher.find())
					child.delete();
			}
		}
	}

	public void seek(long index) {
		fileOffset = index % metaData.getMaxFileSize();
		fileIndex = index / metaData.getMaxFileSize();
	}

	public void write(byte[] data) {
		readAndWrite(data, 0, false);

	}

	public byte[] read(int totalAmountToRead) {
		return readAndWrite(null, totalAmountToRead, true);

	}

	private byte[] readAndWrite(byte[] data, int totalAmountToRead, boolean read) {
		if (read)
			data = new byte[totalAmountToRead];
		try {
			ByteBuffer wrappedData = ByteBuffer.wrap(data);
			long maxFileSize = metaData.getMaxFileSize();
			while (wrappedData.position() < wrappedData.capacity()) {
				FileChannel fileChannel = getFileChannel();
				int amountToReadOrWrite = Math.min(wrappedData.capacity() - wrappedData.position(), (int) (maxFileSize - fileOffset));
				wrappedData.limit(wrappedData.position() + amountToReadOrWrite);
				int actualAmount;
				if (read) {
					actualAmount = fileChannel.read(wrappedData, fileOffset);
					if(actualAmount < amountToReadOrWrite) {
						if(actualAmount < 0)
							actualAmount = 0;
						wrappedData.position(wrappedData.position() + (amountToReadOrWrite - actualAmount));
					}
					// if read past EOF, the buffer will have 0's
				} else {
					actualAmount = fileChannel.write(wrappedData, fileOffset);
					if (amountToReadOrWrite != actualAmount)
						throw new RuntimeException("We didn't write what we expected to write, expected: " + amountToReadOrWrite + ", actual: " + actualAmount);
				}
				if (fileOffset + amountToReadOrWrite >= maxFileSize) {
					fileOffset = 0;
					fileIndex++;
				} else {
					fileOffset += amountToReadOrWrite;
				}
			}
			if(!read) {
				long endOfWrite = fileIndex * maxFileSize + fileOffset;
				if (endOfWrite > metaData.getFileLength()) {
					metaData.setFileLength(endOfWrite);
					writeMetaData(getMetaDataFileName(baseFileName), metaData);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to write", e);
		}
		return data;

	}

	private FileChannel getFileChannel() {
		FileChannel fileChannel = fileChannels.get(fileIndex);
		if (fileChannel == null) {
			File f = new File(getCurrentFileName());
			try {
				fileChannel = new RandomAccessFile(f, "rwd").getChannel();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("File " + f.getName() + " not found");
			}
			fileChannels.put(fileIndex, fileChannel);
		}
		return fileChannel;
	}

	private String getCurrentFileName() {
		return baseFileName + "_" + fileIndex;
	}

	public void close() {
		for (FileChannel value : fileChannels.values()) {
			try {
				value.close();
			} catch (IOException e) {
				throw new RuntimeException("Failed to close file");
			}
		}
	}

	public long length() {
		return metaData.getFileLength();
	}

	public void initializeFiles(long maxSize, long offset) {
		
	}
}
