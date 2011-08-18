package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentHashTableHeader {
	private static final int BYTES_IN_A_LONG = 8;
	private long sizeOfHash;
	private byte[] buffer;
	
	PersistentHashTableHeader(long size){
		buffer = new byte[BYTES_IN_A_LONG];
		sizeOfHash = size;
		ByteBuffer.wrap(buffer).putLong(size);
	}
	
	PersistentHashTableHeader(byte[] buffer){
		this.buffer = buffer;
		sizeOfHash = ByteBuffer.wrap(buffer).getLong();
	}
	
	public long getSizeOfHash(){
		return sizeOfHash;
	}
	
	public byte[] getHashTableHeaderBuffer(){
		return buffer;
	}
}
