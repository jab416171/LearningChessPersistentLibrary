package tests;

import persistence.PersistentHashTable;

import junit.framework.TestCase;

public class PersistentHashTableTest extends TestCase {

	private static final String FILENAME = "PHTxxxxx";
	
	public void testCreate() {
		boolean exceptionOccured = false;
		try{
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, 10, 10);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.open(FILENAME).close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, Long.MAX_VALUE, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, 10000, 10000);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentHashTable.open(FILENAME).close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, -1, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, Long.MIN_VALUE, Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, Long.MAX_VALUE, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		try{ 
			PersistentHashTable.delete(FILENAME);
		}catch(Throwable e){
		}
	}

	public void testDelete() {
		
		boolean exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, 0, 0);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentHashTable toDelete = null;
		exceptionOccured = false;
		try{
			toDelete = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toDelete.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testOpen() {
		boolean exceptionOccured = false;
		try{
			PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.create(FILENAME, 10, 10);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentHashTable toCloseLinkList = null;
		exceptionOccured = false;
		try{
			toCloseLinkList = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toCloseLinkList.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testGet() {
		boolean exceptionOccured = false;
		PersistentHashTable toTest = null;
		try{
			PersistentHashTable.create(FILENAME, 32, 10);
			toTest = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 11);
			toTest.put(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 21);
			toTest.put(new byte[]{2,2,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 31);
			toTest.put(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 41);
			toTest.put(new byte[]{4,4,4,4,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 51);
			toTest.put(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 61);
			toTest.put(new byte[]{6,6,6,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 71);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		long value = 0;
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(value == 11);
		
		
		exceptionOccured = false;
		try{
			value = toTest.remove(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		value = 0;
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(value == 61);
		
		value = 0;
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(value == 41);
		
		value = 0;
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{7,7,7,7,7,7,7,7,7,7,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(value == -1);
		
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{3,3,3,3});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		value = 0;
		exceptionOccured = false;
		try{
			value = toTest.get(new byte[]{3,3,3,3,4,5,6,7,8,9,12,12,12,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testPut() {
		boolean exceptionOccured = false;
		PersistentHashTable toTest = null;
		try{
			PersistentHashTable.create(FILENAME, 32, 10);
			toTest = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 11);
			toTest.put(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 21);
			toTest.put(new byte[]{2,2,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 31);
			toTest.put(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 41);
			toTest.put(new byte[]{4,4,4,4,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 51);
			toTest.put(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 61);
			toTest.put(new byte[]{6,6,6,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 71);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 1000);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{7,7,7,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{8,8,8,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{9,9,9,9,9,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		assertFalse(exceptionOccured);
		long value = 0;
		value = toTest.get(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 1000);
		value = toTest.get(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 61);
		value = toTest.get(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 41);
		value = toTest.get(new byte[]{7,7,7,7,7,7,7,7,7,7,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == -1);
		value = toTest.get(new byte[]{7,7,7,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == Long.MAX_VALUE);
		value = toTest.get(new byte[]{8,8,8,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == Long.MIN_VALUE);
		value = toTest.get(new byte[]{9,9,9,9,9,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == -1);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testRemove() {
		try{
			PersistentHashTable.delete(FILENAME);
		}catch(Throwable e){
			
		}
		boolean exceptionOccured = false;
		PersistentHashTable toTest = null;
		try{
			PersistentHashTable.create(FILENAME, 32, 10);
			toTest = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.put(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 11);
			toTest.put(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 21);
			toTest.put(new byte[]{2,2,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 31);
			toTest.put(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 41);
			toTest.put(new byte[]{4,4,4,4,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 51);
			toTest.put(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 61);
			toTest.put(new byte[]{6,6,6,6,6,6,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 71);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		long value = 0;
		value = toTest.get(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 11);
		value = toTest.get(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 61);
		value = toTest.get(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == 41);
		
		exceptionOccured = false;
		try{
			toTest.remove(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
			toTest.remove(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
			toTest.remove(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		value = toTest.get(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == -1);
		value = toTest.get(new byte[]{5,5,5,5,5,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == -1);
		value = toTest.get(new byte[]{3,3,3,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		assertTrue(value == -1);
		
		exceptionOccured = false;
		try{
			toTest.remove(new byte[]{1,1,1,1,1,1,1,1,1,1,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.remove(new byte[]{3});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		exceptionOccured = false;
		try{
			toTest.remove(new byte[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	
	public void testHashCode()
	{
		try{
			PersistentHashTable.delete(FILENAME);
		}catch(Throwable e){
			
		}
		boolean exceptionOccured = false;
		PersistentHashTable toTest = null;
		try{
			PersistentHashTable.create(FILENAME, 10, 10);
			toTest = PersistentHashTable.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.getHash(new byte[24]);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.getHash(new byte[]{1,2,3,4,5,6,7,8,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.getHash(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32});
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentHashTable.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}


}
