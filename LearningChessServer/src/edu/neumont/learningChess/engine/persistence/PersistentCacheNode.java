package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentCacheNode {
	private byte[] key;
	private byte[] value;
	public PersistentCacheNode(byte[] key, byte[] value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public PersistentCacheNode(byte[] serialized, int keySize) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(serialized);
		key = new byte[keySize];
		int valueSize = serialized.length - keySize;
		value = new byte[valueSize];
		byteBuffer.get(key);
		byteBuffer.get(value);
	}
	
	public byte[] serialize() {
		byte[] buffer = new byte[key.length + value.length];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.put(key);
		byteBuffer.put(value);
		buffer = byteBuffer.array();
		return buffer;
	}
	
	public byte[] getKey() {
		return key;
	}
	public byte[] getValue() {
		return value;
	}
	
	
	
}
