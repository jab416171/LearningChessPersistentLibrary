package edu.neumont.persistence;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PersistentArrayHeader {
	
	private long clientHeaderSize;
	private long clientRecordSize;
	private byte[] clientHeader;
	
	
	public PersistentArrayHeader(long clientRecordSize, long clientHeaderSize) {
		this.clientHeaderSize = clientHeaderSize;
		this.clientRecordSize = clientRecordSize;
		setClientHeader(new byte[(int) clientHeaderSize]);
	}
	
	public void read(RandomAccessFile file)
	{
		try {
			clientRecordSize = file.readLong();
			clientHeaderSize = file.readLong();
			file.read(getClientHeader());
			
		} catch (IOException e) {
			throw new RuntimeException("Failure to read PersistentArrayHeader.", e);
		}
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
	
	
	public long getClientRecordSize() {
		return clientRecordSize;
	}

	public long getClientHeaderSize() {
		return clientHeaderSize;
	}

	private byte[] getClientHeader() {
		return clientHeader;
	}

	private void setClientHeader(byte[] clientHeader) {
		
		this.clientHeader = clientHeader.clone();
	}


}
