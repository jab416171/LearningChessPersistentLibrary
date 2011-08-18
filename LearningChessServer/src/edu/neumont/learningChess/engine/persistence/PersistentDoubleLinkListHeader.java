package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentDoubleLinkListHeader
{
	
	private long listHeadIndex, listSize, maxListSize;
	public static final long SIZE = 24;
	
	public void incrementListSize() {
		listSize++;
	}
	
	public void decrementListSize() {
		if(listSize<=1)
			throw new RuntimeException("Can't decrement");
		listSize--;
	}
	
	public  PersistentDoubleLinkListHeader(long listIndex, long listSize, long maxListSize) {
		this.listHeadIndex = listIndex;
		this.listSize = listSize;
		this.maxListSize = maxListSize;
	}
	
	public  PersistentDoubleLinkListHeader(byte[] buffer) {
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

	public long getMaxListSize() {
		return maxListSize;
	}
	
	public byte[] serialize()
	{
		byte[] buffer = new byte[(int) SIZE];
		ByteBuffer serializer = ByteBuffer.wrap(buffer);
		serializer.putLong(listHeadIndex);
		serializer.putLong(listSize);
		serializer.putLong(maxListSize);
		return serializer.array();
	}
	
	public void deserialize(byte[] buffer)
	{
		ByteBuffer deserializer = ByteBuffer.wrap(buffer);
		listHeadIndex = deserializer.getLong();
		listSize = deserializer.getLong();
		maxListSize = deserializer.getLong();
	}
}
