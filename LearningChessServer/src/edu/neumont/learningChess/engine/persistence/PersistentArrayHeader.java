package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

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

	public static PersistentArrayHeader read(PersistentBigFile file) {
		file.seek(0);
		byte[] data = file.read(LONG_SIZE * 2);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		long clientRecordSize = buffer.getLong();
		long clientHeaderSize = buffer.getLong();
		PersistentArrayHeader header = new PersistentArrayHeader(clientRecordSize, clientHeaderSize);
		header.clientHeader = file.read((int) header.getClientHeaderSize());
		return header;
	}

	public void write(PersistentBigFile file) {
		byte[] buffer = new byte[(int) (LONG_SIZE * 2 + getClientHeaderSize())];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putLong(clientRecordSize);
		byteBuffer.putLong(clientHeaderSize);
		byteBuffer.put(getClientHeader());
		file.write(buffer);
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