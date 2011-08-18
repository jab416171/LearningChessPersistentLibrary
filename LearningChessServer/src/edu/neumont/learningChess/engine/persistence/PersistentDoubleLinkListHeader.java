package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentDoubleLinkListHeader
{
	
	private long index, listSize, maxListSize;
	public static final long SIZE = 24;
	
	public  PersistentDoubleLinkListHeader(long listIndex, long listSize, long maxListSize) {
		this.index = listIndex;
		this.listSize = listSize;
		this.maxListSize = maxListSize;
	}
	
	public  PersistentDoubleLinkListHeader(byte[] buffer) {
		deserialize(buffer);
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public long getListSize() {
		return listSize;
	}

	public void setListSize(long listSize) {
		this.listSize = listSize;
	}
	
	public byte[] serialize()
	{
		byte[] buffer = new byte[(int) SIZE];
		ByteBuffer serializer = ByteBuffer.wrap(buffer);
		serializer.putLong(index);
		serializer.putLong(listSize);
		serializer.putLong(maxListSize);
		return serializer.array();
	}
	
	public void deserialize(byte[] buffer)
	{
		ByteBuffer deserializer = ByteBuffer.wrap(buffer);
		index = deserializer.getLong();
		listSize = deserializer.getLong();
		maxListSize = deserializer.getLong();
	}

	public long getMaxListSize() {
		return maxListSize;
	}
	
}
