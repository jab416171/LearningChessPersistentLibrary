package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class PersistentHashTable {

	/**
	 * 
	 *  16 bytes -> 4 bytes using XOR ^
	 *  use the 4 bytes as the hash code
	 *  k % 1 billion
	 *  compare full gamestates, get index for lookup in linkedList
	 * 
	 */
	private PersistentArray persistentArray;
	private static final long INFO_SIZE = 8;
	private static final int BYTES_IN_A_LONG = 8;
	private static final int BITS_IN_A_BYTE = 8;
	
	private long hashSize;
	private long recordSize;
	private byte[] EMPTY_KEY = null;
		
	public static void create(String fileName, long recordSize, long hashSize){
		try {
			PersistentArray.create(fileName, recordSize+INFO_SIZE, BYTES_IN_A_LONG);
			PersistentArray array = PersistentArray.open(fileName);
			array.putHeader(new PersistentHashTableHeader(hashSize).getHashTableHeaderBuffer());
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The hash table, " + fileName +", could not be created", e);
		}
	}

	public static void delete(String fileName){
		try {
			PersistentArray.delete(fileName);
		} catch (Throwable e) {
			throw new RuntimeException("The hash table, " + fileName +", could not be deleted", e);
		}		
	}
	
	public static PersistentHashTable open(String fileName)
	{
		PersistentHashTable tempHashTable = null;
		try{
			tempHashTable = new PersistentHashTable(PersistentArray.open(fileName));
		}
		catch(Throwable e){
			throw new RuntimeException("The hash table, " + fileName + " could not be opened.", e);
		}
		return tempHashTable;
	}
	
	public void close(){
		persistentArray.close();
	}

	
	private PersistentHashTable(PersistentArray array){
		persistentArray = array;
		recordSize = array.getRecordSize();
		hashSize = new PersistentHashTableHeader(array.getHeader()).getSizeOfHash();
		EMPTY_KEY = new byte[(int)(recordSize-INFO_SIZE)];
	}
	
	public long get(byte[] buffer){
		long index = -1;
		HashedLocation location = findIndexLinearProbe(buffer);
		if(location.isFound){
			HashEntry entry = new HashEntry(persistentArray.get(location.index));
			index = entry.value;
		}
		return index;			
	}
	
	public void put(byte[] key, long value){
		HashEntry toPut = new HashEntry(key, value);
		long index = findIndexLinearProbe(key).index;
		persistentArray.put(index, toPut.serializedEntry);

	}
	
	
	public long remove(byte[] buffer){
		long n = -1;
		HashedLocation location = findIndexLinearProbe(buffer);
		HashEntry toRemove = null;
		if(location.isFound) {
			n = location.index;
			toRemove = new HashEntry(persistentArray.get(n));
			persistentArray.remove(n);
			 n++;
			 byte[] current = persistentArray.get(n);
			 while(current != null && !Arrays.equals(current, new byte[current.length])){
				 current = persistentArray.get(n);
				 persistentArray.remove(n);
				 n++;
				 if(current != null){
					 HashEntry currentEntry = new HashEntry(current);
				 	put(currentEntry.key, currentEntry.value);
				 }
			 }
		}
		long pointer = -1;
		if(toRemove != null)
			pointer = toRemove.value;
		return pointer;
	}
	
	
	public void printss()
	{
		persistentArray.printFile();
	}

	
	public long getHash(byte[] toHash){
		long l = 0;
		for (int i = 0; i < toHash.length; ) {
			l ^= getLongFromBytes(toHash, i);
			i+=BYTES_IN_A_LONG;
		}
		long mask = Long.parseLong("7FFFFFFFFFFFFFFF", 16);
		l = l & mask;
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
	 * @param byte[] toFind
	 * @return index of the array or the next free space in the probe
	 */
	private HashedLocation findIndexLinearProbe(byte[] toFind){
		HashedLocation location = null;
		long index = getHash(toFind);
		index = index % hashSize;
		long startingLocation = index;
		
		for (;;) {
			byte[] hashEntryBuffer = persistentArray.get(index);
			if(hashEntryBuffer == null){
				location = new HashedLocation(false, index);
			}
			else{
				HashEntry entry = new HashEntry(hashEntryBuffer);
				location = getEmptyOrFOundLocation(entry.key, toFind, index);
			}
			if (location != null) break;

			index++;
			if (index > hashSize) index = 0;
			if (startingLocation == index) {
				throw new RuntimeException("The HashTable is too full");
			}
		}		
		return location;
	}
	
	
	private HashedLocation getEmptyOrFOundLocation(byte[] key, byte[] toFind, long index) {
		HashedLocation location = null;
		
		if (Arrays.equals(key, EMPTY_KEY)) {
			location = new HashedLocation(false, index);
		} else if (Arrays.equals(key, toFind)) {
			location = new HashedLocation(true, index);
		}
		return location;
	}

	private class HashEntry{
		byte[] key;
		long value;
		byte[] serializedEntry;
		public HashEntry(byte[] buffer){
			serializedEntry = buffer;
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			value = byteBuffer.getLong();
			key = new byte[(int) (recordSize-INFO_SIZE)];
			byteBuffer.get(key, 0, byteBuffer.remaining());
		}
		
		public HashEntry(byte[] key, long value){
			serializedEntry = new byte[(int) recordSize];
			ByteBuffer byteBuffer = ByteBuffer.wrap(serializedEntry);
			byteBuffer.putLong(value);
			byteBuffer.put(key);
		}
	}
	
	private class HashedLocation{
		private boolean isFound;
		private long index;
		public HashedLocation(boolean isFound, long index){
			this.isFound = isFound;
			this.index = index;
		}
	}
}
