package edu.neumont.learningChess.engine;

import edu.neumont.learningChess.engine.persistence.*;

public class GameStateCache {

	private PersistentDoubleLinkList linkedList;
	private PersistentHashTable hashTable;
	private long cacheSize; 
	private static final String HASHTABLE_EXTENSION = "HashTable";
	private static final String LINKEDLIST_EXTENSION = "LinkedList";
	private static final int FLOAT_SIZE = 4;
	
	public static void create(String arrayName, long recordSize, long maxListSize)
	{
		try{
			PersistentHashTable.create(arrayName + HASHTABLE_EXTENSION, recordSize, (long)(maxListSize * 1.3));
			PersistentDoubleLinkList.create(arrayName + LINKEDLIST_EXTENSION, recordSize + (2*FLOAT_SIZE), maxListSize);
		}catch(Throwable e){
			throw new RuntimeException("The cache, " + arrayName + ", could not be created.", e);
		}
	}
	
	public static void delete(String arrayName)
	{
		try{
			PersistentHashTable.delete(arrayName + HASHTABLE_EXTENSION);
			PersistentDoubleLinkList.delete(arrayName + LINKEDLIST_EXTENSION);
		}catch(Throwable e){
			throw new RuntimeException("The cache, " + arrayName + ", could not be deleted.", e);
		}
	}
	
	public static GameStateCache open(String arrayName)
	{
		try{
			PersistentHashTable tempHashTable = PersistentHashTable.open(arrayName + HASHTABLE_EXTENSION);
			PersistentDoubleLinkList tempLinkList = PersistentDoubleLinkList.open(arrayName + LINKEDLIST_EXTENSION);
		return new GameStateCache(tempHashTable, tempLinkList);
		}catch(Throwable e){
			throw new RuntimeException("The cache, " + arrayName + ", could not be deleted.", e);
		}
	}
	
	public void close()
	{
		linkedList.close();
		hashTable.close();
	}
	
	private GameStateCache(PersistentHashTable hashTable, PersistentDoubleLinkList linkList)
	{
		this.linkedList = linkList;
		this.hashTable = hashTable;
		this.cacheSize = linkList.getMaxListSize();
	}
	
	/**
	 * try get gamestate from hash
	 * 	if found
	 * 		use index to find node in linked list
	 * 		update node info (old gamestateinfo = new gamestateinfo)
	 * 		move node to front
	 * else
	 * 		add new node to front of list
	 * 		get the new nodes index
	 * 		add gamestate - index into hash table
	 * 		if to many nodes
	 * 			get gamestate from linked list
	 * 			remove the node from list
	 * 			look for gamestate in hash and remove from hash
	 */
	public void put(GameStateInfo gameStateInfo)
	{
		long index = hashTable.get(gameStateInfo.getGameState().serialize());
		if(index == -1){
			if(linkedList.getListLength() >= cacheSize )
			{
				GameStateInfo lastUsedGameInfo = new GameStateInfo(linkedList.getLastNodeBuffer());
				linkedList.removeLastNode();
				hashTable.remove(lastUsedGameInfo.getGameState().serialize());
			}
			long positionOfNewNode = linkedList.addNodeToFront(gameStateInfo.getSerializedGameStateInfo(), index);
			hashTable.put(gameStateInfo.getGameState().serialize(), positionOfNewNode);
		}
		else{
			linkedList.addNodeToFront(gameStateInfo.getSerializedGameStateInfo(), index);
		}
	}
	
	public GameStateInfo get(GameState gameState)
	{
		GameStateInfo gameInfo = null;
		long index = hashTable.get(gameState.serialize());
		if(index != -1){
			byte[] buffer = linkedList.get(index);
			gameInfo = new GameStateInfo(buffer);
		}
		return gameInfo;
	}
	
	public boolean Remove(GameState gameState){
		boolean wasRemoved = false;
		long positionOfRemoved = hashTable.remove(gameState.serialize());
		if(positionOfRemoved >= 0)
		{
			byte[] removed = linkedList.removeNode(positionOfRemoved);
			if(removed != null)
				wasRemoved = true;			
		}
		else
			throw new RuntimeException("Not found to delete");
		return wasRemoved;
	}
	
	public void printLinkList(){
		linkedList.printFile();
	}
	
	public void printHash(){
		hashTable.printss();
	}
	
	public long linkedListLength()
	{
	return linkedList.getListLength(); 	
	}
	
}
