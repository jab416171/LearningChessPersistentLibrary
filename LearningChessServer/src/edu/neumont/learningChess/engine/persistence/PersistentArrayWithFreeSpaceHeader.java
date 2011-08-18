package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentArrayWithFreeSpaceHeader{
	private static final long LONG_SIZE = 8;
	private long listPointer;
	private byte[] userHeader;
	
	public PersistentArrayWithFreeSpaceHeader(long pointer, byte[] userHeader){
		this.listPointer = pointer;
		this.userHeader = userHeader;
	}
	
	public PersistentArrayWithFreeSpaceHeader(byte[] buffer){
		deserialize(buffer);
	}
	
	private byte[] serialize(){
		byte[] buffer = new byte[(int)LONG_SIZE + userHeader.length];
	    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
	    byteBuffer.putLong(listPointer);
	    byteBuffer.put(userHeader);
		return byteBuffer.array();
	}
	
	private void deserialize(byte[] buffer){
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		this.listPointer = byteBuffer.getLong();
		userHeader = new byte[byteBuffer.remaining()];
		byteBuffer.get(userHeader, 0, byteBuffer.remaining());
	}
	
	public long getSize() {
		return userHeader.length + LONG_SIZE;
	}
	
	public byte[] getSerializedFreeSpaceHeader(){
		return serialize();
	}
	
	public long getPointer(){
		return listPointer;
	}
	
	public void setPointer(long pointer){
		this.listPointer = pointer;
	}
	
	public byte[] getUserHeader(){
		return userHeader;
	}
	
	public void setUserHeader(byte[] buffer){
		this.userHeader = buffer;
	}
}