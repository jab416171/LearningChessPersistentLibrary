package tests;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;
import edu.neumont.learningChess.engine.persistence.PersistentCache;

public class PersistentCacheTest extends TestCase {
	private static final String FILENAME = "PCTxxxxx";
	private static Random random = new Random();

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreate() {
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

		boolean exceptionOccured = false;
		try {
			PersistentCache.open(FILENAME);
			fail("no exception thrown");
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		PersistentCache.create(FILENAME, 100, 100, 100);
		exceptionOccured = false;
		try {
			PersistentCache.create(FILENAME, 100, 100, 100);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentCache.open(FILENAME).close();
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

	}

	public void testDelete() {
		boolean exceptionOccured = false;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}

		assertTrue(exceptionOccured);
		exceptionOccured = false;

		try {
			PersistentCache.create(FILENAME, 100, 100, 100);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

	}

	public void testOpen() {
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

		boolean exceptionOccured = false;
		try {
			PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentCache.create(FILENAME, 100, 100, 100);
			PersistentCache.open(FILENAME).close();
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try {
			PersistentCache.delete(FILENAME);
			PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testClose() {
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

		boolean exceptionOccured = false;
		PersistentCache cache = null;

		try {
			PersistentCache.create(FILENAME, 100, 100, 100);
			cache = PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try {
			cache.close();
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			cache.put(new byte[]{0, 0, 0, 0, 1, 1, 1, 1}, new byte[]{101, 01, 1, 1, 1});
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testPut() {
		PersistentCache cache = null;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

		try {
			PersistentCache.create(FILENAME, 8, 10, 10);
			cache = PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			fail(e + " Couldn't create/open array");
		}

		boolean exceptionOccured = false;
		try {
			cache.put(new byte[]{0, 1, 0, 0, 0, 0, 0, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
			cache.put(new byte[]{0, 0, 1, 0, 0, 0, 0, 1}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
			cache.put(new byte[]{0, 0, 0, 1, 0, 0, 1, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		assertTrue(cache.count() == 3);
		exceptionOccured = false;
		try {
			cache.put(new byte[]{0, 0, 0, 0, 1, 0, 0, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
			cache.put(new byte[]{0, 0, 0, 0, 0, 1, 0, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
			cache.put(new byte[]{0, 0, 0, 0, 0, 0, 1, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(cache.count() == 6);

		exceptionOccured = false;

		try {
			cache.put(new byte[]{1, 0, 0, 0, 0, 0, 0, 0}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			cache.put(new byte[]{1, 1, 1, 1, 1, 1, 1, 1}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			cache.put(new byte[]{1, 1, 1, 1, 1, 1, 1, 1}, new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1});
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		assertTrue(cache.count() == 8);
		
		for (int i = 0; i < 5; i++) {
			cache.put(getRandomByte(8), getRandomByte(8));
		}
		
		cache.close();
	}

	private byte[] getRandomByte(int i) {
		byte[] buffer = new byte[i];
		for (int j = 0; j < buffer.length; j++) {
			
			buffer[j] = (byte) random.nextInt(Byte.MAX_VALUE);
		}
		return buffer;
	}

	public void testGet() {
		PersistentCache cache = null;
		try {
			PersistentCache.delete(FILENAME);
		} catch (Throwable e) {
		}

		try {
			PersistentCache.create(FILENAME, 8, 10, 10);
			cache = PersistentCache.open(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e + " Couldn't create/open array");
		}

		boolean exceptionOccured = false;
		byte[] firstKey = new byte[]{0, 1, 0, 0, 0, 0, 0, 0};
		byte[] firstValue = new byte[]{111, 01, 1, 1, 1, 101, 01, 1, 1, 1};
		byte[] secondKey = new byte[]{0, 0, 1, 0, 0, 0, 0, 1};
		byte[] secondValue = new byte[]{101, 11, 1, 1, 1, 101, 01, 1, 1, 1};
		byte[] thirdKey = new byte[]{0, 0, 0, 1, 0, 0, 1, 0};
		byte[] thirdValue = new byte[]{101, 01, 0, 1, 1, 101, 01, 1, 1, 1};
		try {
			cache.put(firstKey, firstValue);
			cache.put(secondKey, secondValue);
			cache.put(thirdKey, thirdValue);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		System.out.println(Arrays.toString(cache.get(firstKey)));

		assertTrue(cache.count() == 3);
		exceptionOccured = false;
		byte[] fourthKey = new byte[]{0, 0, 0, 0, 1, 0, 0, 0};
		byte[] fourthValue = new byte[]{101, 01, 1, 1, 1, 101, 01, 1, 1, 1};
		byte[] fifthKey = new byte[]{0, 0, 0, 0, 0, 1, 0, 0};
		byte[] fifthValue = new byte[]{111, 11, 1, 1, 1, 101, 11, 1, 1, 1};
		byte[] sixthKey = new byte[]{0, 0, 0, 0, 0, 0, 1, 0};
		byte[] sixthValue = new byte[]{101, 01, 1, 1, 1, 111, 01, 1, 1, 1};
		try {
			cache.put(fourthKey, fourthValue);
			cache.put(fifthKey, fifthValue);
			cache.put(sixthKey, sixthValue);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(cache.count() == 6);

		exceptionOccured = false;

		byte[] seventhKey = new byte[]{1, 0, 0, 0, 0, 0, 0, 0};
		byte[] seventhValue = new byte[]{101, 01, 1, 1, 1, 101, 11, 1, 1, 1};
		try {
			cache.put(seventhKey, seventhValue);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		byte[] eighthKey = new byte[]{1, 1, 1, 1, 1, 1, 1, 1};
		byte[] eighthValue = new byte[]{101, 01, 1, 1, 1, 101, 01, 0, 1, 1};
		try {
			cache.put(eighthKey, eighthValue);
		} catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		assertTrue(cache.count() == 8);
		System.out.println(Arrays.toString(cache.get(firstKey)));
		assertTrue(Arrays.equals(cache.get(firstKey), firstValue));
		assertTrue(Arrays.equals(cache.get(secondKey), secondValue));
		assertTrue(Arrays.equals(cache.get(thirdKey), thirdValue));
		assertTrue(Arrays.equals(cache.get(fourthKey), fourthValue));
		assertTrue(Arrays.equals(cache.get(fifthKey), fifthValue));
		assertTrue(Arrays.equals(cache.get(sixthKey), sixthValue));
		assertTrue(Arrays.equals(cache.get(seventhKey), seventhValue));
		assertTrue(Arrays.equals(cache.get(eighthKey), eighthValue));
	}

}
