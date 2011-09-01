package edu.neumont.learningChess.engine.persistence;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class PersistentArrayWithFreeSpace {
	
	private PersistentArray persistentArray;
	private PersistentArrayWithFreeSpaceHeader header;
	private static final long END_OF_LIST = -1;
	

	private PersistentArrayWithFreeSpace(String fileName){
		persistentArray = PersistentArray.open(fileName);
		getLocalHeader();
	}
	
	public static void create(String fileName, long recordSize, long headerSize, byte[] buffer){
		try {
			PersistentArrayWithFreeSpaceHeader persistentArrayWithFreeSpaceHeader = new PersistentArrayWithFreeSpaceHeader(END_OF_LIST,buffer);
			PersistentArray.create(fileName, recordSize, persistentArrayWithFreeSpaceHeader.getSize());
			PersistentArray array = PersistentArray.open(fileName);
			array.putHeader(persistentArrayWithFreeSpaceHeader.getSerializedFreeSpaceHeader());
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The persistent array, " + fileName +", could not be created", e);
		}
	}
	
	private void getLocalHeader() {
		byte[] buffer = this.persistentArray.getHeader();
		header = new PersistentArrayWithFreeSpaceHeader(buffer);
	}
	
	private void putLocalHeader() {
		byte[] buffer = header.getSerializedFreeSpaceHeader();
		if(buffer.length != header.getSize())
			throw new RuntimeException("Header file size mismatch, buffer length is " + buffer.length + ", header size is " + header.getSize());
		this.persistentArray.putHeader(buffer);
	}
	
	public static void delete(String fileName){
		PersistentArray.delete(fileName);
	}
	
	public static PersistentArrayWithFreeSpace open(String fileName) {
		PersistentArrayWithFreeSpace tempPersistArray = new PersistentArrayWithFreeSpace(fileName);
		return tempPersistArray;
	}
	
	public long allocate(){
		long result = -1;
		long nextFreeIndex = header.getPointer();
		if(nextFreeIndex == END_OF_LIST){
			result = putAllocatedSpace();
		}
		else{
			result = nextFreeIndex;
			long newPointer = ByteBuffer.wrap(persistentArray.get(result)).getLong();
			header.setPointer(newPointer);
			putLocalHeader();
		}
		return result;
	}
	
	private long putAllocatedSpace() {
		byte[] buffer = new byte[(int) persistentArray.getRecordSize()];
		long position = persistentArray.count();
		persistentArray.put(position, buffer);
		return position;
	}

	public void deallocate(long position){
		byte[] buffer = new byte[(int) persistentArray.getRecordSize()];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putLong(header.getPointer());
		header.setPointer(position);
		persistentArray.put(position, byteBuffer.array());
		putLocalHeader();
	}

	public void close() {
		persistentArray.close();
	}
	
	public byte[] getHeader(){
		return header.getUserHeader();
	}
	
	public void put(long position, byte[] buffer){
		long count = persistentArray.count();
		if(position >= count)
			throw new RuntimeException("Can't write past the end of the file, index was " + position + ", size is " + count);
		persistentArray.put(position, buffer);
	}
	
	public byte[] get(long position){
		return persistentArray.get(position);
	}
	
	public void putHeader(byte[] buffer){
		header.setUserHeader(buffer);
		putLocalHeader();
	}
	
	public void printFile(PrintStream printStream){
		persistentArray.printFile(printStream);
	}

	
}	