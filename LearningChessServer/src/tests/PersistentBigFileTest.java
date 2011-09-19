package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import edu.neumont.learningChess.engine.persistence.PersistentBigFile;

public class PersistentBigFileTest {

	private static final String FILENAME = "PBFTest";

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSeek() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRW2() {

		PersistentBigFile.create(FILENAME);
		PersistentBigFile file = PersistentBigFile.open(FILENAME);
		byte[] data = new byte[]{0,0,0,0,0,0,1,2,3,4,5,6,7,8,9,10,11,12};
		file.write(data);
		file.seek(6);
		byte[] readData = file.read(8);
		byte[] expectedReadData = new byte[]{1,2,3,4,5,6,7,8};
		System.out.println(Arrays.toString(readData));
		System.out.println(Arrays.toString(expectedReadData));
		assertTrue(Arrays.equals(readData,expectedReadData));
		file.close();
		PersistentBigFile.delete(FILENAME);
	}

	@Test
	public void testWriteAndRead() {
		readAndWrite(20);
		readAndWrite(8);
		readAndWrite(16);
		readAndWrite(1024);
		readAndWrite(4);
//		try {PersistentBigFile.delete(FILENAME);} catch(Exception e){
//			e.printStackTrace();
//		}
//		PersistentBigFile.create(FILENAME, MAX_FILE_SIZE);
//		PersistentBigFile file = PersistentBigFile.open(FILENAME);
//		file.seek(0);
//		byte[] readData = file.read(10);
//		System.out.println(Arrays.toString(readData));
//		assertTrue(Arrays.equals(new byte[]{1,2,3,4,0,0,0,0,0,0},readData));
//		file.close();
		try {PersistentBigFile.delete(FILENAME);} catch(Exception e){}
		
	}

	private void readAndWrite(int i) {
		try {PersistentBigFile.delete(FILENAME);} catch(Exception e){}
		PersistentBigFile.create(FILENAME);
		PersistentBigFile file = PersistentBigFile.open(FILENAME);
		file.seek(0);
		byte[] writtenData = new byte[i];
		for (int j = 0; j < writtenData.length; j++) {
			writtenData[j] = (byte) (j + 1);
		}
		file.write(writtenData);
		file.seek(0);
		byte[] readData = file.read(writtenData.length);
		assertTrue(Arrays.equals(writtenData,readData));
		file.close();
	}

	@Test
	public void testLength() {
		fail("Not yet implemented");
	}
}