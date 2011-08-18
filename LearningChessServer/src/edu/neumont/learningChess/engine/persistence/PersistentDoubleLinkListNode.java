package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentDoubleLinkListNode {

	static final long LONG_SIZE = 8;
	private long frontPointer, backPointer;
	private byte[] data;
	
	public PersistentDoubleLinkListNode(byte[] serializedNode) {
		deserialize(serializedNode);
	}
	
	public PersistentDoubleLinkListNode(byte[] data, long frontPointer, long backPointer) {
		this.data = data;
		this.frontPointer = frontPointer;
		this.backPointer = backPointer;
	}
	
	private byte[] serialize(){
		byte[] serializedBuffer = new byte[(int) (LONG_SIZE + LONG_SIZE + data.length)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedBuffer);
		byteBuffer.putLong(frontPointer);
		byteBuffer.putLong(backPointer);
		byteBuffer.put(data);
		return byteBuffer.array();
	}
	

	private void deserialize(byte[] serializedNode){
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedNode);
		frontPointer = byteBuffer.getLong();
		backPointer = byteBuffer.getLong();
		data = new byte[byteBuffer.remaining()];
		byteBuffer.get(data, 0, byteBuffer.remaining());
	}
	
	public long getFrontPointer() {
		return frontPointer;
	}

	public void setFrontPointer(long frontPointer) {
		this.frontPointer = frontPointer;
	}

	public long getBackPointer() {
		return backPointer;
	}

	public void setBackPointer(long backPointer) {
		this.backPointer = backPointer;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}	
	
	public long getSerializedSize()	{
		return LONG_SIZE + LONG_SIZE + data.length;
	}
	
	public byte[] getSerializedNode(){
		return serialize();
	}

}
