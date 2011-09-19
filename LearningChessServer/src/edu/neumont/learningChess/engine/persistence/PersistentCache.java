package edu.neumont.learningChess.engine.persistence;

public class PersistentCache {
	private PersistentDoubleLinkList linkedList;
	private PersistentHashTable hashTable;
	private PersistentCacheHeader header;
	private static final String HASHTABLE_EXTENSION = "HashTable";
	private static final String LINKEDLIST_EXTENSION = "LinkedList";
	
	private PersistentCache(String fileName)
	{
		this.linkedList = PersistentDoubleLinkList.open(getLinkedListName(fileName));
		this.hashTable = PersistentHashTable.open(getHashTableName(fileName));
		header = new PersistentCacheHeader(linkedList.getHeader());
	}
	
	
	public static void create(String fileName, long keySize, long valueSize, long cacheSize)
	{
		if(keySize % 4 != 0) {
			throw new RuntimeException("Key Size must be a multiple of 4");
		}
		PersistentCacheHeader header = new PersistentCacheHeader(cacheSize);
		PersistentHashTable.create(getHashTableName(fileName), keySize, (long)(cacheSize * 1.3));
		PersistentDoubleLinkList.create(getLinkedListName(fileName), keySize + valueSize, header.getSize());
		PersistentDoubleLinkList list = PersistentDoubleLinkList.open(getLinkedListName(fileName));
		list.putHeader(header.serialize());
		list.close();
	}

	public static void delete(String arrayName)
	{
		PersistentHashTable.delete(getHashTableName(arrayName));
		PersistentDoubleLinkList.delete(getLinkedListName(arrayName));
	}
	
	public static PersistentCache open(String fileName)
	{
		return new PersistentCache(fileName);
	}
	
	public void close()
	{
		linkedList.close();
		hashTable.close();
	}
	
	public void put(byte[] key, byte[] value) {
		PersistentCacheNode node = new PersistentCacheNode(key, value);
		long index = hashTable.get(key);
		if(index >= 0) {
			linkedList.update(index, node.serialize());
			linkedList.moveToFront(index);
		} else {
			if(linkedList.getLength() >= header.getCacheSize()) {
				byte[] oldNodeBuffer = linkedList.removeFromBack();
				PersistentCacheNode oldNode = new PersistentCacheNode(oldNodeBuffer,key.length);
				hashTable.remove(oldNode.getKey());
			}
			long newNodeIndex = linkedList.addToFront(node.serialize());
			hashTable.put(key, newNodeIndex);
		}
		
	}
	
	public byte[] get(byte[] key) {
		long index = hashTable.get(key);
		byte[] value = null;
		if(index >= 0) {
			byte[] buffer = linkedList.get(index);
			PersistentCacheNode node = new PersistentCacheNode(buffer, key.length);
			value = node.getValue();
			linkedList.moveToFront(index);
		}
		return value;
	}

	private static String getHashTableName(String arrayName) {
		return arrayName + HASHTABLE_EXTENSION;
	}

	private static String getLinkedListName(String arrayName) {
		return arrayName + LINKEDLIST_EXTENSION;
	}


	public long count() {
		return linkedList.getLength();
	}


	public void putHeader(byte[] buffer) {
		header.deserialize(buffer);
	}


	public byte[] getHeader() {
		return header.serialize();
	}


	public void initializeFiles() {
		hashTable.initializeFiles();
	}
}