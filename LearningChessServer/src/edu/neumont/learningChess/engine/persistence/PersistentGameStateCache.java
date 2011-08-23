package edu.neumont.learningChess.engine.persistence;

import edu.neumont.learningChess.engine.GameStateInfo;

public class PersistentGameStateCache {
	private PersistentCache cache;
	
	private PersistentGameStateCache(String fileName)
	{
		this.cache = PersistentCache.open(fileName);
	}
	
	public static void create(String fileName, long keySize, long valueSize, long recordSize){
		try {
			PersistentCache.create(fileName, keySize, valueSize, recordSize);
			PersistentCache array = PersistentCache.open(fileName);
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The persistent array, " + fileName +", could not be created", e);
		}
	}
	
	public static void delete(String fileName){
		PersistentCache.delete(fileName);
	}
	
	public static PersistentGameStateCache open(String fileName) {
		PersistentGameStateCache tempCache = new PersistentGameStateCache(fileName);
		return tempCache;
	}
	
	public void close() {
		cache.close();
	}
	
	public void put(Game key, GameStateInfo value) {
		cache.put(key.serialize(), value.getGameStateBuffer());
	}
	
	public GameStateInfo get(Game key) {
		GameStateInfo gsi = new GameStateInfo(cache.get(key.serialize()));
		return gsi;
	}
	
	
}