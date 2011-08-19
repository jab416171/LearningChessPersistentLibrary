package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentDoubleLinkListHeader
{
	
	private long listHeadIndex, listSize;
	private byte[] clientHeader;
	private static final long LONG_SIZE = 8;
	
	public void incrementListSize() {
		listSize++;
	}
	
	public void decrementListSize() {
		if(listSize<=0)
			throw new RuntimeException("Can't decrement");
		listSize--;
	}
	
	public PersistentDoubleLinkListHeader(long listIndex, byte[] clientHeader) {
		this.listHeadIndex = listIndex;
		this.clientHeader = clientHeader;
		listSize = 0;
	}
	
	public PersistentDoubleLinkListHeader(byte[] buffer) {
		deserialize(buffer);
	}

	public long getListHeadIndex() {
		return listHeadIndex;
	}

	public void setListHeadIndex(long listHeadIndex) {
		this.listHeadIndex = listHeadIndex;
	}

	public long getListSize() {
		return listSize;
	}

	public void setListSize(long listSize) {
		this.listSize = listSize;
	}

	public byte[] serialize()
	{
		byte[] buffer = new byte[(int) getHeaderSize()];
		ByteBuffer serializer = ByteBuffer.wrap(buffer);
		serializer.putLong(listHeadIndex);
		serializer.putLong(listSize);
	    serializer.put(clientHeader);
		return serializer.array();
	}

	public void deserialize(byte[] buffer)
	{
		ByteBuffer deserializer = ByteBuffer.wrap(buffer);
		listHeadIndex = deserializer.getLong();
		listSize = deserializer.getLong();
		clientHeader = new byte[deserializer.remaining()];
		deserializer.get(clientHeader, 0, deserializer.remaining());
	}
	
	private long getHeaderSize() {
		return LONG_SIZE + LONG_SIZE + clientHeader.length;
	}

	public byte[] getClientHeader() {
		return clientHeader;
	}

	public void setClientHeader(byte[] clientHeader) {
		this.clientHeader = clientHeader;
	}
}
