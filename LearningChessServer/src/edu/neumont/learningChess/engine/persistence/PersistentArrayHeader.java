package edu.neumont.learningChess.engine.persistence;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PersistentArrayHeader {
	
	private long clientHeaderSize;
	private long clientRecordSize;
	private static final int LONG_SIZE = 8;
	private byte[] clientHeader;
	
	
	public PersistentArrayHeader(long clientRecordSize, long clientHeaderSize) {
		this.clientHeaderSize = clientHeaderSize;
		this.clientRecordSize = clientRecordSize;
		setClientHeader(new byte[(int) clientHeaderSize]);
	}
	
	public static PersistentArrayHeader read(RandomAccessFile file)
	{
		PersistentArrayHeader header = null;
		try {
			long clientRecordSize = file.readLong();
			long clientHeaderSize = file.readLong();
			header = new PersistentArrayHeader(clientRecordSize, clientHeaderSize);
			file.read(header.clientHeader);
			
		} catch (IOException e) {
			throw new RuntimeException("Failure to read PersistentArrayHeader.", e);
		}
		return header;
	}
	
	public void write(RandomAccessFile file)
	{
		try {
			file.writeLong(clientRecordSize);
			file.writeLong(clientHeaderSize);
			file.write(getClientHeader());
			
		} catch (IOException e) {
			throw new RuntimeException("Failure to write PersistentArrayHeader.", e);
		}
	}
	
	public long getHeaderSize() {
		return (LONG_SIZE * 2) + getClientHeaderSize();
	}
	
	public long getClientRecordSize() {
		return clientRecordSize;
	}

	public long getClientHeaderSize() {
		return clientHeaderSize;
	}

	public byte[] getClientHeader() {
		return clientHeader;
	}

	public void setClientHeader(byte[] clientHeader) {
		
		this.clientHeader = clientHeader.clone();
	}


}
