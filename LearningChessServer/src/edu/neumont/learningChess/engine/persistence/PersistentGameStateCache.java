package edu.neumont.learningChess.engine.persistence;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.engine.GameStateInfo;
import edu.neumont.learningChess.engine.SerializedChessGameState;

public class PersistentGameStateCache {
	private PersistentCache cache;
	public static final long KEY_SIZE = SerializedChessGameState.getRecordSize();
	public static final long VALUE_SIZE = GameStateInfo.getRecordSize();
	
	private PersistentGameStateCache(String fileName) {
		this.cache = PersistentCache.open(fileName);
	}
	
	public synchronized static void create(String fileName, long recordSize) {
		try {
			PersistentCache.create(fileName, KEY_SIZE, VALUE_SIZE, recordSize);
			PersistentCache array = PersistentCache.open(fileName);
			array.close();
		} catch (Throwable e) {
			throw new RuntimeException("The persistent array, " + fileName +", could not be created", e);
		}
	}
	
	public synchronized static void delete(String fileName) {
		PersistentCache.delete(fileName);
	}
	
	public synchronized static PersistentGameStateCache open(String fileName) {
		PersistentGameStateCache tempCache = new PersistentGameStateCache(fileName);
		return tempCache;
	}
	
	public synchronized void close() {
		cache.close();
	}
	
	public synchronized void put(ChessGameState key, GameStateInfo value) {
		cache.put(serialize(key), value.serialize());
	}
	
	public synchronized GameStateInfo get(ChessGameState key) {
		byte[] cacheBuffer = cache.get(serialize(key));
		return cacheBuffer == null ? 
				new GameStateInfo(0,0) : new GameStateInfo(cacheBuffer);
	}
	
	private byte[] serialize(ChessGameState game) {
		return SerializedChessGameState.serialize(game);
	}

	public void initializeFiles() {
		cache.initializeFiles();
	}
}