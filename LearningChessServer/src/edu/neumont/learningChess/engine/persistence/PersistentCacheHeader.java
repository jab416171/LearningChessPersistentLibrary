package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentCacheHeader {
	private static final int LONG_SIZE = 8;
	private long cacheSize;

	public PersistentCacheHeader(long cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	public PersistentCacheHeader(byte[] buffer) {
		deserialize(buffer);
	}
	
	public byte[] serialize() {
		byte[] buffer = new byte[LONG_SIZE];
		return ByteBuffer.wrap(buffer).putLong(cacheSize).array();
	}
	
	public void deserialize(byte[] buffer) {
		this.cacheSize = ByteBuffer.wrap(buffer).getLong();
	}
	
	public long getCacheSize() {
		return cacheSize;
	}
	
	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

	public long getSize() {
		return LONG_SIZE;
	}
	
}