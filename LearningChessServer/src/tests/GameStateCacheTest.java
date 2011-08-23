package tests;

import ai.GameState;
import ai.GameStateInfo;
import persistence.GameStateCache;
import junit.framework.TestCase;

public class GameStateCacheTest extends TestCase {

	private static final String FILENAME = "GSCxxxxx";
	
	public void testCreate() {
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
		}
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 10, 10);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.open(FILENAME).close();
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, Long.MAX_VALUE, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 10000, 10000);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentGameStateCache.open(FILENAME).close();
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, -1, Long.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, Long.MIN_VALUE, Long.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, Long.MAX_VALUE, -1);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		try{ 
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
		}
	}

	public void testDelete() {
		
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 0, 0);
		}
		catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentGameStateCache toDelete = null;
		exceptionOccured = false;
		try{
			toDelete = PersistentGameStateCache.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toDelete.close();
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testOpen() {
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 32, 10);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		PersistentGameStateCache toCloseLinkList = null;
		exceptionOccured = false;
		try{
			toCloseLinkList = PersistentGameStateCache.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			toCloseLinkList.close();
			PersistentGameStateCache.delete(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			PersistentGameStateCache.open(FILENAME);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	
	public void testGet() {
		try{
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
		}
		PersistentGameStateCache toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 32, 5);
			toTest = PersistentGameStateCache.open(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		GameStateInfo gameInfo= null;
		GameStateInfo compareGameInfo = null;
		//1
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{0,1,2,3,4,5,6,7,8,9 ,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 5F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//2
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 10F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//3
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{2,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 15F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{2,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//4
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{3,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 23F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{3,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		//5
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{4,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 43F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{4,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//6
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo == null);
		
		//7
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{4,7,8,9}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		//8
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{5,5,5,5,5,4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo == null);
		
		//end
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testPut() {
		try{
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
		}
		PersistentGameStateCache toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 32, 5);
			toTest = PersistentGameStateCache.open(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		GameStateInfo gameInfo= null;
		GameStateInfo compareGameInfo = null;
		//1
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 5F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//2
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 10F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{1,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//3
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{2,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 15F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{2,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//4
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{3,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 23F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{3,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		//5
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{4,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 43F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			compareGameInfo = toTest.get(new GameState(new byte[]{4,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		//6
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, Float.MAX_VALUE, Float.MAX_VALUE));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		//7
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{4,7,8,9}, 5F, 5F));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		//8
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{5,5,5,5,5,4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 5F, 5F));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		//9
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, Float.MIN_VALUE, Float.MIN_VALUE));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		//10
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 0, 0));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		//11
		exceptionOccured = false;
		try{
			toTest.put(new GameStateInfo(new byte[]{4,6,6,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, -1, -1));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		//end
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

	public void testRemove() {
		try{
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
		}
		PersistentGameStateCache toTest = null;
		boolean exceptionOccured = false;
		try{
			PersistentGameStateCache.create(FILENAME, 32, 10);
			toTest = PersistentGameStateCache.open(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		GameStateInfo gameInfo= null;
		//GameStateInfo compareGameInfo = null;
//		//1
//		exceptionOccured = false;
//		try{
//			gameInfo = new GameStateInfo(new byte[]{0,1,2,3,4,5,6,7,8,9}, 5F, 5F);
//			toTest.put(gameInfo);
//		}catch(Throwable e){
//			e.printStackTrace();
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{0,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
//				&& compareGameInfo.getCount() == gameInfo.getCount());
//		
//		//2
//		exceptionOccured = false;
//		try{
//			gameInfo = new GameStateInfo(new byte[]{1,1,2,3,4,5,6,7,8,9}, 10F, 5F);
//			toTest.put(gameInfo);
//		}catch(Throwable e){
//			e.printStackTrace();
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{1,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
//				&& compareGameInfo.getCount() == gameInfo.getCount());
//		
//		//3
//		exceptionOccured = false;
//		try{
//			gameInfo = new GameStateInfo(new byte[]{2,1,2,3,4,5,6,7,8,9}, 15F, 5F);
//			toTest.put(gameInfo);
//			toTest.Remove(new GameState(new byte[]{2,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			e.printStackTrace();
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{2,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo == null);
//		
		//4
		exceptionOccured = false;
		try{
			gameInfo = new GameStateInfo(new byte[]{3,3,3,3,3,3,3,3,3,3,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 23F, 5F);
			toTest.put(gameInfo);
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);

		//5
		exceptionOccured = false;
		try{
//toTest.printLinkList();
			gameInfo = new GameStateInfo(new byte[]{6,6,6,6,6,6,6,6,6,6,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}, 43F, 5F);
			toTest.put(gameInfo);
//toTest.printLinkList();
//System.out.println("before remove linked list size = " + toTest.linkedListLength());
			//toTest.printHash();
			toTest.Remove(new GameState(new byte[]{3,3,3,3,3,3,3,3,3,3,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
			//toTest.printHash();
			toTest.Remove(new GameState(new byte[]{6,6,6,6,6,6,6,6,6,6,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32}));
			//toTest.printHash();

			
			
//toTest.printLinkList();
//System.out.println("after remove linked list size = " + toTest.linkedListLength());
		}catch(Throwable e){
			e.printStackTrace();
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
		
		exceptionOccured = false;
		try{
			//compareGameInfo = toTest.get(new GameState(new byte[]{6,6,6,6,6,6,6,6,6,6}));
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo.getAverage() == gameInfo.getAverage() 
//				&& compareGameInfo.getCount() == gameInfo.getCount());
		
		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{4,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo == null);
//		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{3,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo == null);
//		
//		exceptionOccured = false;
//		try{
//			compareGameInfo = toTest.get(new GameState(new byte[]{4,1,2,3,4,5,6,7,8,9}));
//		}catch(Throwable e){
//			exceptionOccured = true;
//		}
//		assertFalse(exceptionOccured);
//		assertTrue(compareGameInfo == null);
//	
		
		//end
		exceptionOccured = false;
		try{
			toTest.close();
			PersistentGameStateCache.delete(FILENAME);
		}catch(Throwable e){
			exceptionOccured = true;
		}
		assertFalse(exceptionOccured);
	}

}
