package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentDoubleLinkListNode {

	static final long LONG_SIZE = 8;
	private long frontPointer, backPointer;
	private byte[] buffer;
	private byte[] serializedNode;
	
	public PersistentDoubleLinkListNode(byte[] serializedNode) {
		this.serializedNode = serializedNode;
		deserialize();
	}
	
	public PersistentDoubleLinkListNode(byte[] buffer, long frontPointer, long backPointer) {
		this.buffer = buffer;
		this.frontPointer = frontPointer;
		this.backPointer = backPointer;
		serialize();
	}
	
	private void serialize(){
		byte[] serializedBuffer = new byte[(int) (LONG_SIZE + LONG_SIZE + buffer.length)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedBuffer);
		byteBuffer.putLong(frontPointer);
		byteBuffer.putLong(backPointer);
		byteBuffer.put(buffer);
		serializedNode = serializedBuffer;
	}
	

	private void deserialize(){
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedNode);
		frontPointer = byteBuffer.getLong();
		backPointer = byteBuffer.getLong();
		buffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(buffer, 0, byteBuffer.remaining());
	}
	
	/**
	 * Method to get the size of the serialized buffer.
	 * Serialized buffer is buffer plus frontPointer and backPointer.
	 * @return
	 */
	public long getSerializedSize()	{
		return serializedNode.length;
	}
	
	public byte[] getSerializedNode(){
		return serializedNode;
	}

	public long getFrontPointer() {
		return frontPointer;
	}

	public void setFrontPointer(long frontPointer) {
		this.frontPointer = frontPointer;
		serialize();
	}

	public long getBackPointer() {
		return backPointer;
	}

	public void setBackPointer(long backPointer) {
		this.backPointer = backPointer;
		serialize();
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
		serialize();
	}	
}
