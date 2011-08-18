package tests;
import java.util.Arrays;

import junit.framework.TestCase;
import edu.neumont.learningChess.engine.persistence.PersistentArray;


public class PersistArrayTest extends TestCase {
	private static final String FILENAME = "PAxxxxx";
	public void testCreate() {
		clearContents();
		boolean exceptionOccured = false;
		try {
			PersistentArray.open(FILENAME);
			fail("no exception thrown");
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		//PersistentArray.create("x", 100);
		
		PersistentArray.create(FILENAME, 100, 100);
		exceptionOccured = false;
		try {
			PersistentArray.create(FILENAME, 100, 100);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentArray.open(FILENAME).close();
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			PersistentArray.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		clearContents();
	}

	public void testDelete() {
		clearContents();
		//Delete before it exists (should throw exception)
		boolean exceptionOccured = false;
		try {
			PersistentArray.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//Creates the new file (should not throw exception)
		exceptionOccured = false;
		try {
			PersistentArray.create(FILENAME, 100, 100);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Deletes created file (should not throw exception)
		exceptionOccured = false;
		try {
			PersistentArray.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Opens file that has been deleted (should throw exception)
		exceptionOccured = false;
		try {
			PersistentArray.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//Tries deleting file that has been deleted (should throw exception)
		exceptionOccured = false;
		try {
			PersistentArray.delete(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testOpen() {
		clearContents();
		//Tries to open non-existent file
		boolean exceptionOccured = false;
		try {
			PersistentArray.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		//creates file to open, and opens it, then closes it.
		exceptionOccured = false;
		try {
			PersistentArray.create(FILENAME, 100, 100);
			PersistentArray.open(FILENAME).close();
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		//Deletes file, then tries to open it.
		exceptionOccured = false;
		try {
			PersistentArray.delete(FILENAME);
			PersistentArray.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testClose() {
		clearContents();
		boolean exceptionOccured = false;
		PersistentArray array = null;
		try {
			PersistentArray.create(FILENAME, 100, 100);
			array = PersistentArray.open(FILENAME);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		try {
			array.close();
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try {
			array.put(1000, new byte[]{101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		clearContents();
	}

	public void testPut() {
		PersistentArray array = null;
		clearContents();
		try{
			PersistentArray.create(FILENAME, 10, 10);
			array = PersistentArray.open(FILENAME);
		}
		catch(Throwable e){
			fail( e + " Couldn't create/open array");
		}
		
		boolean exceptionOccured = false;
		try {
			array.put(0, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(1, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(2, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		assertTrue(array.count() == 3);
		exceptionOccured = false;
		try {
			array.put(0, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(1, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(3, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(array.count() == 4);
		exceptionOccured = false;
		
		try {
			array.put(-1, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			array.put(Long.MAX_VALUE, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			array.put(Long.MIN_VALUE, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		
		try{
			array.close();
			PersistentArray.delete(FILENAME);
		}
		catch(Throwable e){
		}
	}

	public void testGet() {
		PersistentArray array = null;
		clearContents();
		try{
			PersistentArray.create(FILENAME, 10, 10);
			array = PersistentArray.open(FILENAME);
		}
		catch(Throwable e){
			fail( e + " Couldn't create/open array");
		}
		boolean exceptionOccured = false;
		try {
			array.put(0, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(1, new byte[]{102,01,1,1,1,101,01,1,1,1});
			array.put(2, new byte[]{103,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		
		try {
			array.get(Long.MIN_VALUE);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			array.get(Long.MAX_VALUE);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			array.get(-1);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			array.get(10);
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try {
			assertTrue(Arrays.equals(array.get(2), new byte[]{103,01,1,1,1,101,01,1,1,1}));
		}
		catch (Throwable e) {
			System.out.println(e);
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			array.close();
			PersistentArray.delete(FILENAME);
		}
		catch(Throwable e){
		}
	}

	private void clearContents() {
		try{
			PersistentArray.delete(FILENAME);
		}catch(Throwable e){
		}
	}

	public void testCount() {
		PersistentArray array = null;
		clearContents();
		try{
			PersistentArray.create(FILENAME, 10, 10);
			array = PersistentArray.open(FILENAME);
		}
		catch(Throwable e){
			fail( e + " Couldn't create/open array");
		}
		boolean exceptionOccured = false;
		try {
			array.put(0, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(1, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(2, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(array.count() == 3);
		exceptionOccured = false;
		try {
			array.put(0, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(1, new byte[]{101,01,1,1,1,101,01,1,1,1});
			array.put(3, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(array.count() == 4);
		exceptionOccured = false;
		try {
			array.put(10000, new byte[]{101,01,1,1,1,101,01,1,1,1});
		}
		catch (Throwable e) {
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(array.count() == 10001);
		exceptionOccured = false;
		try{
			array.close();
			PersistentArray.delete(FILENAME);
		}
		catch(Throwable e){
		}
	}

	public void testGetPutHeader(){
		PersistentArray testArray = null;
		boolean exceptionOccured = false;
		clearContents();
		try{
			PersistentArray.create(FILENAME, 10, 10);
			testArray = PersistentArray.open(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		byte[] header = null;
		try{
			 header = new byte[]{0,1,2,3,4,5,6,7,8,9};
			testArray.putHeader(header);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(Arrays.equals(header, testArray.getHeader()));
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			 header = new byte[]{1,2,3,4,5,6,7,8,9,10};
			testArray.putHeader(header);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(Arrays.equals(header, testArray.getHeader()));
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			 header = new byte[10];
			testArray.putHeader(header);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(Arrays.equals(header, testArray.getHeader()));
		assertFalse(exceptionOccured);
		exceptionOccured = false;
		try{
			 header = null;
			testArray.putHeader(header);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(Arrays.equals(header, testArray.getHeader()));
		assertTrue(exceptionOccured);
		exceptionOccured = false;
		try{
			testArray.close();
			PersistentArray.delete(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}
}
