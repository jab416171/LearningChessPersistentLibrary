package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

public class PersistentArrayWithFreeSpace {
	
	private PersistentArray persistentArray;
	private PersistentFreeSpaceHeader freeSpace;
	private static final long LONG_SIZE = 8;
	private static final long END_OF_LIST = -1;
	
	public static void create(String fileName, long recordSize, long headerSize, byte[] buffer){
		try {
			PersistentArray.create(fileName, recordSize, headerSize + LONG_SIZE);
			PersistentArray array = PersistentArray.open(fileName);
			byte[] headerBuffer = new PersistentFreeSpaceHeader(END_OF_LIST,buffer).getSerializedFreeSpaceHeader();
			array.putHeader(headerBuffer);
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The persistent array, " + fileName +", could not be created", e);
		}
	}
	
	public static void delete(String fileName){
		try {
			PersistentArray.delete(fileName);
		} catch (Throwable e) {
			throw new RuntimeException("The persistent array, " + fileName +", could not be deleted", e);
		}
	}
	
	public static PersistentArrayWithFreeSpace open(String fileName) {
		PersistentArrayWithFreeSpace tempPersistArray = null;
		try{
			tempPersistArray = new PersistentArrayWithFreeSpace(PersistentArray.open(fileName));
		}
		catch(Throwable e){
			throw new RuntimeException("The persistent array, " + fileName + " could not be opened.", e);
		}
		return tempPersistArray;
	}
	
	private PersistentArrayWithFreeSpace(PersistentArray array){
		persistentArray = array;
		freeSpace = new PersistentFreeSpaceHeader(persistentArray.getHeader());
	}
	
	public long allocate(){
		long result = -1;
		long nextFreeIndex = freeSpace.getPointer();
		if(nextFreeIndex == END_OF_LIST){
			result = putAllocatedSpace();
		}
		else{
			result = freeSpace.getPointer();
			long newPointer = ByteBuffer.wrap(persistentArray.get(result)).getLong();
			freeSpace.setPointer(newPointer);
			persistentArray.putHeader(freeSpace.getSerializedFreeSpaceHeader());
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
		byteBuffer.putLong(freeSpace.getPointer());
		freeSpace.setPointer(position);
		persistentArray.put(position, byteBuffer.array());
	}

	public void close() {
		persistentArray.close();
	}
	
	public byte[] getHeader(){
		return freeSpace.getHeader();
	}
	
	public void put(long position, byte[] buffer){
		persistentArray.put(position, buffer);
	}
	
	public byte[] get(long position){
		return persistentArray.get(position);
	}
	
	public void putHeader(byte[] buffer){
		freeSpace.setUserHeader(buffer);
		persistentArray.putHeader(freeSpace.getSerializedFreeSpaceHeader());
	}
	
	
	public void printFile(){
		persistentArray.printFile();
	}
	
}	
