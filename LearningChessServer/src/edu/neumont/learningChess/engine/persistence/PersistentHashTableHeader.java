package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentHashTableHeader {
	private static final int BYTES_IN_A_LONG = 8;
	private long tableLength;
	
	PersistentHashTableHeader(long size){
		tableLength = size;
	}
	
	PersistentHashTableHeader(byte[] buffer){
		deserialize(buffer);
	}

	private void deserialize(byte[] buffer) {
		tableLength = ByteBuffer.wrap(buffer).getLong();
	}
	
	public byte[] serialize(){
		byte[] buffer = new byte[BYTES_IN_A_LONG];
		ByteBuffer.wrap(buffer).putLong(tableLength);
		return buffer;
	}
	
	public long getTableLength(){
		return tableLength;
	}
}
