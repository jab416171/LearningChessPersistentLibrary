package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import edu.neumont.learningChess.engine.persistence.PersistentDoubleLinkList;

public class oldPersistentDoubleLinkListTest extends TestCase {
	private static final String FILENAME = "PDLLxxxxx";
	public void testCreate() {
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, 10, 10);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.open(FILENAME).close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, Long.MAX_VALUE, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, 10000, 10000);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.open(FILENAME).close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, -1, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, Long.MIN_VALUE, Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, Long.MAX_VALUE, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		try{ 
			PersistentDoubleLinkList.delete(FILENAME);
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	public void testDelete() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, 0, 0);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentDoubleLinkList toDelete = null;
		exceptionOccured = false;
		try{
			toDelete = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toDelete.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testOpen() {boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME, 10, 10);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentDoubleLinkList toCloseLinkList = null;
		exceptionOccured = false;
		try{
			toCloseLinkList = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toCloseLinkList.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testAddNodeToFront() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		long one = 0,two = 0,three =0;
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			one = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,1,2,3,4,5,6,7,8,9};
			two = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			three = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			toTest.addNodeToFront(buffer, two);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		assertTrue(Arrays.equals(toTest.get(one), new byte[]{0,2,4,6,8,1,3,5,7,9}));
		assertTrue(Arrays.equals(toTest.get(two), new byte[]{0,2,4,6,8,1,3,5,7,9}));
		assertTrue(Arrays.equals(toTest.get(three), new byte[]{9,8,7,6,5,4,3,2,1,0}));
		
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			three = toTest.addNodeToFront(buffer, 1000000);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{3,3,3,3,3,3,3,3,3,3,3,3,3}, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{0,1,2,3,4,5,6,7,8,9}, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{0,1,2,3,4,5,6,7,8,9}, Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{0,1,2,3,4,5,6,7,8,9}, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testGetListLength() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,1,2,3,4,5,6,7,8,9};
			toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			toTest.addNodeToFront(buffer, 2);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			toTest.addNodeToFront(buffer, 1000000);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{3,3,3,3,3,3,3,3,3,3,3,3,3}, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		exceptionOccured = false;
		try{
			toTest.addNodeToFront(new byte[]{0,1,2,3,4,5,6,7,8,9}, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		exceptionOccured = false;
		try{
			toTest.removeLastNode();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		exceptionOccured = false;
		try{
			toTest.removeNode(0);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testGetMaxListSize() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		long size = 0;
		exceptionOccured = false;
		try{
			size = toTest.getMaxListSize();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(size == 10);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,100000000);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		size = 0;
		exceptionOccured = false;
		try{
			size = toTest.getMaxListSize();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(size == 100000000);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10, Long.MAX_VALUE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		size = 0;
		exceptionOccured = false;
		try{
			size = toTest.getMaxListSize();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(size == Long.MAX_VALUE);
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testRemoveNode() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		long one = 0,two = 0,three =0;
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			one = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,1,2,3,4,5,6,7,8,9};
			two = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			three = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		

		exceptionOccured = false;
		try{
			buffer = toTest.removeNode(two);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertFalse(Arrays.equals(toTest.get(two), buffer));
		
		exceptionOccured = false;
		try{
			buffer = toTest.removeNode(three);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertFalse(Arrays.equals(toTest.get(three), buffer));
		
		exceptionOccured = false;
		try{
			buffer = toTest.removeNode(one);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 0);
		assertTrue(Arrays.equals(toTest.get(one), buffer));
		
		
		exceptionOccured = false;
		try{
			toTest.removeNode(-1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.removeNode(Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testGet() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		long two = 0,three =0;
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,1,2,3,4,5,6,7,8,9};
			two = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			three = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		

		exceptionOccured = false;
		try{
			buffer = toTest.get(two);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(new byte[]{0,1,2,3,4,5,6,7,8,9}, buffer));
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(two);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(new byte[]{0,1,2,3,4,5,6,7,8,9}, buffer));
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(three);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(new byte[]{9,8,7,6,5,4,3,2,1,0}, buffer));
		
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(4);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(-1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(-1000000);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			buffer = toTest.get(Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testRemoveLastNode() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		byte[] tempBuffer = null;
		long one = 0,two = 0;
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,2,4,6,8,1,3,5,7,9};
			one = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{0,1,2,3,4,5,6,7,8,9};
			two = toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		
		exceptionOccured = false;
		try{
			buffer = new byte[]{9,8,7,6,5,4,3,2,1,0};
			toTest.addNodeToFront(buffer, -1);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(toTest.getFrontNodeBuffer(), buffer));
		

		exceptionOccured = false;
		try{
			tempBuffer = toTest.get(one);
			buffer = toTest.removeLastNode();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(Arrays.equals(tempBuffer, buffer));
		assertTrue(toTest.getListLength() == 2);
		assertFalse(Arrays.equals(toTest.get(one), buffer));
		
		exceptionOccured = false;
		try{
			buffer = toTest.removeLastNode();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertFalse(Arrays.equals(toTest.get(two), buffer));
		
		exceptionOccured = false;
		try{
			
			buffer = toTest.removeLastNode();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 0);
		//assertFalse(Arrays.equals(toTest.get(three), buffer));
		
		
		exceptionOccured = false;
		try{
			toTest.removeLastNode();
			toTest.removeLastNode();
			toTest.removeLastNode();
			toTest.removeLastNode();
			toTest.removeLastNode();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.removeNode(Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testGetLastNodeBuffer() {
		try{
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentDoubleLinkList.create(FILENAME,10,10);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		exceptionOccured = false;
		try{
			
			toTest.addNodeToFront(new byte[]{0,2,4,6,8,1,3,5,7,9}, -1);
			buffer = toTest.getLastNodeBuffer();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 1);
		assertTrue(Arrays.equals(new byte[]{0,2,4,6,8,1,3,5,7,9}, buffer));
		
		
		try{
			
			toTest.addNodeToFront(new byte[]{1,1,1,1,8,1,3,5,7,9}, -1);
			buffer = toTest.getLastNodeBuffer();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 2);
		assertTrue(Arrays.equals(new byte[]{0,2,4,6,8,1,3,5,7,9}, buffer));
		
		
		try{
			
			toTest.addNodeToFront(new byte[]{0,0,0,0,0,0,0,0,0,0}, -1);
			buffer = toTest.getLastNodeBuffer();
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(toTest.getListLength() == 3);
		assertTrue(Arrays.equals(new byte[]{0,2,4,6,8,1,3,5,7,9}, buffer));
		
		
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

}
