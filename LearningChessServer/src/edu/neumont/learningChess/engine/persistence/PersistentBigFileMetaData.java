package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentBigFileMetaData {
	private static final int LONG_SIZE = 8;
	private long maxFileSize;
	private long fileLength;
	
	public PersistentBigFileMetaData(long maxFileSize, long fileLength) {
		super();
		this.maxFileSize = maxFileSize;
		this.fileLength = fileLength;
	}
	
	public PersistentBigFileMetaData(byte[] buffer) {
		deserialize(buffer);
	}
	
	private void deserialize(byte[] buffer) {
		ByteBuffer wrap = ByteBuffer.wrap(buffer);
		maxFileSize = wrap.getLong();
		fileLength = wrap.getLong();
	}
	
	public byte[] serialize() {
		byte[] buffer = new byte[LONG_SIZE * 2];
		ByteBuffer wrap = ByteBuffer.wrap(buffer);
		wrap.putLong(maxFileSize);
		wrap.putLong(fileLength);
		return wrap.array();
	}
	/**
	 * @return the fileLength
	 */
	public long getFileLength() {
		return fileLength;
	}
	/**
	 * @param fileLength the fileLength to set
	 */
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	/**
	 * @return the maxFileSize
	 */
	public long getMaxFileSize() {
		return maxFileSize;
	}
	
	public static int getMetaDataSize() {
		return LONG_SIZE * 2;
	}
}
