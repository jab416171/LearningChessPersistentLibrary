package edu.neumont.learningChess.engine.persistence;

import java.io.PrintStream;

public class PersistentArray {
	private static final long HEADER_OFFSET = 0;
	private PersistentBigFile bigFile;
	private PersistentArrayHeader header;

	private PersistentArray(String fileName) {
		bigFile = PersistentBigFile.open(fileName);
	}

	private void readHeader() {
		bigFile.seek(HEADER_OFFSET);
		header = PersistentArrayHeader.read(bigFile);
	}

	private void writeHeader() {
		bigFile.seek(HEADER_OFFSET);
		header.write(bigFile);
	}

	public static void create(String fileName, long recordSize, long headerSize) {
		PersistentBigFile.create(fileName);
		PersistentArray persistentArray = new PersistentArray(fileName);
		persistentArray.header = new PersistentArrayHeader(recordSize, headerSize);
		persistentArray.writeHeader();
		persistentArray.close();
	}

	public static void delete(String fileName) {
		PersistentBigFile.delete(fileName);
	}

	public static PersistentArray open(String fileName) {
		PersistentArray persistentArray = new PersistentArray(fileName);
		persistentArray.readHeader();
		return persistentArray;
	}

	public void close() {
		bigFile.close();
	}

	public void putHeader(byte[] buffer) {
		if (buffer.length != header.getClientHeaderSize())
			throw new RuntimeException("Header file size mismatch, buffer length is " + buffer.length + ", clientHeaderSize is " + header.getClientHeaderSize());
		header.setClientHeader(buffer);
		writeHeader();
	}

	public byte[] getHeader() {
		return header.getClientHeader();
	}

	public void put(long index, byte[] buffer) {
		if (buffer.length > header.getClientRecordSize())
			throw new RuntimeException("The buffer being put is larger than record size. Size mismatch error. Buffer length: " + buffer.length + ", header size: " + header.getClientHeaderSize());
		if (index >= (Long.MAX_VALUE - 1) || index < 0)
			throw new RuntimeException("Put location error, index was " + index);
		long offset = getOffset(index);
		bigFile.seek(offset);
		bigFile.write(buffer);
	}

	private long getMaxIndex() {
		return (Long.MAX_VALUE - header.getHeaderSize()) / header.getClientRecordSize();
	}

	public byte[] get(long index) {
		if (index >= getMaxIndex() || index < 0)
			throw new RuntimeException("Trying to get something from beyond file: " + index + ".");

		long offset = getOffset(index);
		bigFile.seek(offset);
		// end of file will keep the buffer empty
		byte[] buffer = bigFile.read((int) header.getClientRecordSize());
		return buffer;
	}

	public long count() {
		long length = 0;
		length = bigFile.length();
		return ((length - header.getHeaderSize()) / header.getClientRecordSize());
	}

	public void printFile(PrintStream printStream) {
		printStream.println("Not implemented");
		// printStream.println("Start print file----------------");
		// try {
		// arrayStream.seek(0);
		// for (int i = 0; i < arrayStream.length(); i++) {
		// printStream.println(i + ": " + arrayStream.read());
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// printStream.println("end print file----------------");
	}

	private long getOffset(long index) {
		return header.getHeaderSize() + (index * header.getClientRecordSize());
	}

	public void remove(long index) {
		byte[] buffer = new byte[(int) header.getClientRecordSize()];
		this.put(index, buffer);
	}

	public long getRecordSize() {
		return header.getClientRecordSize();
	}
	
	public void initializeFiles(long maxSize) {
		long offset = getOffset(0);
		final long CHUNK_SIZE = 100000000L;
		bigFile.seek(offset);
		for (long i = maxSize; i > 0; i-= Math.max(maxSize % CHUNK_SIZE, CHUNK_SIZE)) {
			bigFile.write(new byte[(int) maxSize]);
		}
	}

}