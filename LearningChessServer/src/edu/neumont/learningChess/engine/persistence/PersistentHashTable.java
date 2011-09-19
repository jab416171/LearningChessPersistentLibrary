package edu.neumont.learningChess.engine.persistence;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PersistentHashTable {

	/**
	 * 
	 *  16 bytes -> 4 bytes using XOR ^
	 *  use the 4 bytes as the hash code
	 *  k % table size
	 * 
	 */
	private PersistentArray persistentArray;
	private static final long INFO_SIZE = 8;
	private static final int BYTES_IN_A_LONG = 8;
	private static final int BITS_IN_A_BYTE = 8;
	
	private long hashSize; 
	private long recordSize;
	private static final long LOW_ORDER_MASK = 0x7FFFFFFFFFFFFFFFL;
		 
	private PersistentHashTable(String fileName){
		persistentArray = PersistentArray.open(fileName);
		recordSize = persistentArray.getRecordSize();
		hashSize = new PersistentHashTableHeader(persistentArray.getHeader()).getTableLength();
	}
	
	public static void create(String fileName, long keyLength, long tableLength){
		if(keyLength <= 0)
			throw new RuntimeException("Record size must be positive");
			
		try {
			PersistentArray.create(fileName, keyLength+INFO_SIZE, BYTES_IN_A_LONG);
			PersistentArray array = PersistentArray.open(fileName);
			array.putHeader(new PersistentHashTableHeader(tableLength).serialize());
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The hash table, " + fileName +", could not be created", e);
		}
	}

	public static void delete(String fileName){
		PersistentArray.delete(fileName);
	}
	
	public static PersistentHashTable open(String fileName)
	{
		return new PersistentHashTable(fileName);
	}
	
	public void close(){
		persistentArray.close();
	}

	
	
	public long get(byte[] key){
		long index = -1;
		Long location = findIndexLinearProbe(key);
		HashEntry entry = getEntry(location);
		index = isEmpty(entry.key) ? -1 : entry.value;
		return index;			
	}

	private HashEntry getEntry(Long location) {
		return new HashEntry(persistentArray.get(location));
	}
	
	public void put(byte[] key, long value){
		HashEntry toPut = new HashEntry(key, value);
		long index = findIndexLinearProbe(key);
		persistentArray.put(index, toPut.serialize());

	}
	
	private boolean isEmpty(byte[] key) {
		return Arrays.equals(key, new byte[key.length]);
	}
	
	
	public long remove(byte[] buffer){
		HashEntry toRemove = null;
		long index = findIndexLinearProbe(buffer);
		toRemove = getEntry(index);
		persistentArray.remove(index);
		
		for(;;) {
			index++;
			if(index >= hashSize)
				index = 0;
			byte[] current = persistentArray.get(index);
			if(isEmpty(current))
				break;
			persistentArray.remove(index);
			HashEntry currentEntry = new HashEntry(current);
			put(currentEntry.key, currentEntry.value);
		}
		return toRemove.value;
	}
	
	public void printFile(PrintStream printStream)
	{
		persistentArray.printFile(printStream);
	}
	
	public long getHash(byte[] toHash){
		long l = 0;
		for (int i = 0; i < toHash.length; ) {
			l ^= getLongFromBytes(toHash, i);
			i+=BYTES_IN_A_LONG;
		}
		
		l &= LOW_ORDER_MASK;
		return l;
	}
	
	private long getLongFromBytes(byte[] buffer, int index) {
		long l = 0;
		for (int i = 0; i < BYTES_IN_A_LONG; i++) {
			l |= ((long)buffer[index + i]) << (i*BITS_IN_A_BYTE);
		}
		return l;
	}
	
	/**
	 * Method for finding the index of the byte[] in the hash table using Linear probing hash.
	 * If the specified byte[] is not in the table, return the next free space where the hash could go.
	 * @param byte[] key
	 * @return index of the array or the next free space in the probe
	 */
	private Long findIndexLinearProbe(byte[] key){
		Long location = null;
		long index = getHash(key);
		index = index % hashSize;
		long startingLocation = index;
		
		for (;;) {
			byte[] hashEntryBuffer = persistentArray.get(index);
			HashEntry entry = new HashEntry(hashEntryBuffer);
			location = getEmptyOrFoundLocation(entry.key, key, index);
			if (location != null) break;

			index++;
			if (index >= hashSize) index = 0;
			if (startingLocation == index) {
				throw new RuntimeException("The HashTable is too full");
			}
		}		
		return location;
	}
	
	
	private Long getEmptyOrFoundLocation(byte[] key, byte[] toFind, long index) {
		Long location = null;
		
		if (isEmpty(key) || Arrays.equals(key, toFind)) {
			location = index;
		}
		return location;
	}
	
	public void initializeFiles() {
		persistentArray.initializeFiles(hashSize * recordSize);
	}

	private class HashEntry{
		byte[] key;
		long value;
		public HashEntry(byte[] serializedEntry){
			deserialize(serializedEntry);
		}
		
		public HashEntry(byte[] key, long value){
			this.key = key;
			this.value = value;
		}
		
		public byte[] serialize() {
			byte[] buffer = new byte[(int) recordSize];
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			byteBuffer.putLong(value);
			byteBuffer.put(key);
			return byteBuffer.array();
		}
		
		public void deserialize(byte[] buffer) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			value = byteBuffer.getLong();
			key = new byte[(int) (recordSize-INFO_SIZE)];
			byteBuffer.get(key, 0, byteBuffer.remaining());
		}
	}

}