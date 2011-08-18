package tests;
import java.util.Arrays;

import junit.framework.TestCase;
import edu.neumont.learningChess.engine.persistence.PersistentArrayWithFreeSpace;

public class PersistentArrayFreeSpaceTest extends TestCase {
	private static final String FILENAME = "PAFSxxxxx";
	public void testCreate() {
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
		}
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.open(FILENAME);
			fail("no exception thrown");
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		try{
			PersistentArrayWithFreeSpace.create(FILENAME, 100, 10 , new byte[]{1,1,1,1,1,1,1,1,1,1});
		}catch(Throwable e){
			e.printStackTrace();
			fail("1st create");
		}
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 100, 10 , new byte[]{1,1,1,1,1,1,1,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.open(FILENAME).close();
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testDelete() {
		//Delete before it exists (should throw exception)
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//Creates the new file (should not throw exception)
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 100, 10 , new byte[10]);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		//Opens free space array
		PersistentArrayWithFreeSpace toBeClosed = null;
		exceptionOccured = false;
		try {
			toBeClosed = 
				PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Tries to be deleted before closing the file.
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		toBeClosed.close();
		
		//Deletes created file (should not throw exception)
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Opens file that has been deleted (should throw exception)
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//Tries deleting file that has been deleted (should throw exception)
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testOpen() {
		//Tries to open non-existent file
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//creates file to open, and opens it, then closes it.
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 100, 10, new byte[10]);
			PersistentArrayWithFreeSpace.open(FILENAME).close();
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Deletes file, then tries to open it.
		exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
			PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testGet(){
		PersistentArrayWithFreeSpace testGet = null;
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 10, 10, new byte[10]);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testGet = PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false; 
		assertEquals(0, testGet.allocate());
		assertEquals(1, testGet.allocate());
		assertEquals(2, testGet.allocate());
		try {
			testGet.put(0, new byte[]{1,2,3,4,5,6,7,8,9,10});
			testGet.put(1, new byte[]{0,1,2,3,4,5,6,7,8,9});
			testGet.put(2, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		try {
			byte[] got = testGet.get(1);
			assertTrue(Arrays.equals(got, new byte[]{0,1,2,3,4,5,6,7,8,9}));
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		try {
			testGet.get(1000);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			assertTrue(testGet.get(-1000) == null);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			assertTrue(testGet.get(-1) == null);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try{
			assertTrue(testGet.get(Long.MAX_VALUE) == null);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try{
			assertTrue(testGet.get(Long.MIN_VALUE) == null);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testGet.close();
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}
	
	public void testPut(){
		PersistentArrayWithFreeSpace testPut = null;
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 10, 10, new byte[10]);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testPut = PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		try {
			testPut.put(0, new byte[]{1,2,3,4,5,6,7,8,9,10});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		assertEquals(0,testPut.allocate());
		exceptionOccured = false;
		try {
			testPut.put(0, new byte[]{1,2,3,4,5,6,7,8,9,10});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		assertTrue(Arrays.equals(testPut.get(0), new byte[]{1,2,3,4,5,6,7,8,9,10}));
		
		exceptionOccured = false;
		try {
			testPut.put(-1, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try {
			testPut.put(Long.MIN_VALUE, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			testPut.put(Long.MAX_VALUE, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testPut.close();
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}
	
	public void testAllocateDeallocate(){
		try {
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
		}
		PersistentArrayWithFreeSpace testAllocate = null;
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 10, 10, new byte[10]);
		}
		catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testAllocate = PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try{
			assertTrue(testAllocate.allocate() == 0);
			assertTrue(testAllocate.allocate() == 1);
			assertTrue(testAllocate.allocate() == 2);
			assertTrue(testAllocate.allocate() == 3);
			exceptionOccured = false;
			try {
				testAllocate.put(4, new byte[10]);
			} catch(Throwable e) {
				exceptionOccured = true;
			}
			assertTrue(exceptionOccured);
			exceptionOccured = false;
			assertTrue(testAllocate.allocate() == 4);
			testAllocate.deallocate(3);
			assertTrue(testAllocate.allocate() == 3);
			assertTrue(testAllocate.allocate() == 5);
			testAllocate.deallocate(1);
			testAllocate.deallocate(2);
			testAllocate.deallocate(3);
			assertTrue(testAllocate.allocate() == 3);
			assertTrue(testAllocate.allocate() == 2);
			assertTrue(testAllocate.allocate() == 1);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		
		try {
			testAllocate.deallocate(-1);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			testAllocate.deallocate(Long.MIN_VALUE);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			testAllocate.deallocate(Long.MAX_VALUE);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testAllocate.close();
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}
	
	public void testGetPutHeader(){
		PersistentArrayWithFreeSpace testPut = null;
		boolean exceptionOccured = false;
		try {
			PersistentArrayWithFreeSpace.create(FILENAME, 10, 10, new byte[10]);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testPut = PersistentArrayWithFreeSpace.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		assertEquals(0,testPut.allocate());
		assertEquals(1,testPut.allocate());
		assertEquals(2,testPut.allocate());
		try {
			testPut.put(0, new byte[]{1,2,3,4,5,6,7,8,9,10});
			testPut.put(1, new byte[]{0,1,2,3,4,5,6,7,8,9});
			testPut.put(2, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		assertTrue(Arrays.equals(testPut.get(0), new byte[]{1,2,3,4,5,6,7,8,9,10}));
		assertTrue(Arrays.equals(testPut.get(1), new byte[]{0,1,2,3,4,5,6,7,8,9}));
		assertTrue(Arrays.equals(testPut.get(2), new byte[]{2,3,4,5,6,7,8,9,10,11}));
		
		exceptionOccured = false;
		try {
			testPut.put(-1, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try {
			testPut.put(Long.MIN_VALUE, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			testPut.put(Long.MAX_VALUE, new byte[]{2,3,4,5,6,7,8,9,10, 11});
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try {
			testPut.close();
			PersistentArrayWithFreeSpace.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}
}
