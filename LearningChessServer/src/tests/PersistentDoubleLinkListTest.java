package tests;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.TODO;

import edu.neumont.learningChess.engine.persistence.PersistentDoubleLinkList;

import junit.framework.TestCase;

public class PersistentDoubleLinkListTest extends TestCase {
	private static final int HEADER_SIZE = 10;
	private static final String FILENAME = "PDLLxxxxx";

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreate() {
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.open(FILENAME).close();
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, Long.MAX_VALUE,
					Long.MAX_VALUE);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, 10000, 10000);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.open(FILENAME).close();
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, -1, Long.MAX_VALUE);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, Long.MIN_VALUE,
					Long.MIN_VALUE);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, Long.MAX_VALUE, -1);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}
		
	}

	public void testDelete() {
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, 0, 0);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentDoubleLinkList toDelete = null;
		exceptionOccured = false;
		try {
			toDelete = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			toDelete.close();
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		toDelete.close();
	}

	public void testOpen() {
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentDoubleLinkList toCloseLinkList = null;
		exceptionOccured = false;
		try {
			toCloseLinkList = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			toCloseLinkList.close();
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testClose() {
		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try {
			toTest.close();
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try {
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		toTest.close();
		
	}

	public void testPutGetHeader() {
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}

		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] header = new byte[]{1,2,3,4,5,6,7,8,9,0};
		toTest.putHeader(header);
		assertEquals(header, toTest.getHeader());
		toTest.close();
	}

	public void testRemoveFromBack() {
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}

		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		long one = 0, two = 0, three = 0, four = 0;
		exceptionOccured = false;
		try {
			buffer = new byte[] { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
			one = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),1);
		assertTrue(Arrays.equals(toTest.get(one), buffer));
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertEquals(toTest.getLength(),0);

		exceptionOccured = false;
		try {
			buffer = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			two = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),1);
		assertTrue(Arrays.equals(toTest.get(two), buffer));

		exceptionOccured = false;
		try {
			buffer = new byte[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
			three = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),2);
		assertTrue(Arrays.equals(toTest.get(three), buffer));
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertEquals(toTest.getLength(),1);
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertEquals(toTest.getLength(),0);
		toTest.close();
		
	}

	public void testAddToFront() {
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		byte[] buffer = null;
		long one = 0, two = 0, three = 0, four = 0;
		exceptionOccured = false;
		try {
			buffer = new byte[] { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
			one = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),1);
		assertTrue(Arrays.equals(toTest.get(one), buffer));

		exceptionOccured = false;
		try {
			buffer = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			two = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),2);
		assertTrue(Arrays.equals(toTest.get(two), buffer));

		exceptionOccured = false;
		try {
			buffer = new byte[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
			three = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),3);
		assertTrue(Arrays.equals(toTest.get(three), buffer));

		exceptionOccured = false;
		try {
			buffer = new byte[] { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
			four = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),4);
		assertTrue(Arrays.equals(toTest.get(four), buffer));
		assertTrue(Arrays.equals(toTest.get(one), new byte[] { 0, 2, 4, 6, 8,
				1, 3, 5, 7, 9 }));
		assertTrue(Arrays.equals(toTest.get(two), new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
		assertTrue(Arrays.equals(toTest.get(three), new byte[] { 9, 8, 7, 6, 5,
				4, 3, 2, 1, 0 }));

		exceptionOccured = false;
		try {
			buffer = new byte[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
			three = toTest.addToFront(buffer);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(toTest.getLength(),5);
		assertTrue(Arrays.equals(toTest.get(three), buffer));

		exceptionOccured = false;
		try {
			toTest.addToFront(new byte[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
					3, 3 });
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try {
			toTest.addToFront(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			toTest.addToFront(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			toTest.addToFront(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			toTest.close();
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		toTest.close();
	}

	public void testMoveToFront() {
		try {
			PersistentDoubleLinkList.delete(FILENAME);
		} catch (Throwable e) {
		}

		PersistentDoubleLinkList toTest = null;
		boolean exceptionOccured = false;
		try {
			PersistentDoubleLinkList.create(FILENAME, HEADER_SIZE, HEADER_SIZE);
			toTest = PersistentDoubleLinkList.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			toTest.moveToFront(1);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		assertEquals(0,toTest.getLength());
		long indexOne = 0, indexTwo = 0, indexThree = 0;
		exceptionOccured = false;
		try {
			indexOne = toTest.addToFront(new byte[]{1,2,3,4,5,6,7,8,9,0});
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		

		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		
		exceptionOccured = false;
		try {
			toTest.moveToFront(indexOne);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		
		exceptionOccured = false;
		try {
			indexTwo = toTest.addToFront(new byte[]{6,7,8,9,0,1,2,3,4,5});
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		

		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		
		exceptionOccured = false;
		try {
			toTest.moveToFront(indexOne);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(1,toTest.getLength());
		assertTrue(Arrays.equals(toTest.get(indexOne),new byte[]{1,2,3,4,5,6,7,8,9,0}));
		
		exceptionOccured = false;
		try {
			indexTwo = toTest.addToFront(new byte[]{6,7,8,9,0,1,2,3,4,5});
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			indexThree = toTest.addToFront(new byte[]{2,4,6,8,0,1,3,5,7,9});
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		

		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		assertTrue(Arrays.equals(new byte[]{2,4,6,8,0,1,3,5,7,9},toTest.get(indexThree)));
		
		exceptionOccured = false;
		try {
			toTest.moveToFront(indexOne);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		assertTrue(Arrays.equals(new byte[]{2,4,6,8,0,1,3,5,7,9},toTest.get(indexThree)));
		
		exceptionOccured = false;
		try {
			toTest.moveToFront(indexTwo);
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		assertTrue(Arrays.equals(new byte[]{2,4,6,8,0,1,3,5,7,9},toTest.get(indexThree)));
		
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9,0},toTest.get(indexOne)));
		assertTrue(Arrays.equals(new byte[]{6,7,8,9,0,1,2,3,4,5},toTest.get(indexTwo)));
		

		assertEquals(2,toTest.getLength());
		
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(1,toTest.getLength());
		
		exceptionOccured = false;
		try {
			toTest.removeFromBack();
		} catch(Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertEquals(0,toTest.getLength());


		
		
		toTest.close();
		
	}

}
