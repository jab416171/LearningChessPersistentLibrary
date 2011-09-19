package edu.neumont.learningChess.engine.persistence;

import java.io.PrintStream;


public class PersistentDoubleLinkList {

	private PersistentArrayWithFreeSpace persistentArrayFS;
	private PersistentDoubleLinkListHeader header;
	private static final int LONG_SIZE = 8;
	
	private PersistentDoubleLinkList(String linkListName)
	{
		this.persistentArrayFS = PersistentArrayWithFreeSpace.open(linkListName);
		header = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
	}
	
	public static void create(String fileName, long recordSize, long headerSize)
	{
		byte[] headerBuffer = new byte[(int) headerSize];
		PersistentDoubleLinkListHeader header = new PersistentDoubleLinkListHeader(0, headerBuffer);
		PersistentArrayWithFreeSpace.create(fileName, recordSize + (2 * LONG_SIZE), headerBuffer.length, header.serialize());
		PersistentArrayWithFreeSpace tempArrayWithFreeSpace = PersistentArrayWithFreeSpace.open(fileName);
		long headNodePosition = tempArrayWithFreeSpace.allocate();
		header.setListHeadIndex(headNodePosition);
		tempArrayWithFreeSpace.putHeader(header.serialize());
		PersistentDoubleLinkListNode node = 
				new PersistentDoubleLinkListNode(new byte[(int)recordSize], headNodePosition, headNodePosition);
		tempArrayWithFreeSpace.put(headNodePosition, node.getSerializedNode());
		
		tempArrayWithFreeSpace.close();
	}

	
	public static void delete(String linkListName)
	{
		PersistentArrayWithFreeSpace.delete(linkListName);
	}
	
	public static PersistentDoubleLinkList open(String linkListName)
	{
		PersistentDoubleLinkList toOpen = new PersistentDoubleLinkList(linkListName);
		return toOpen;
	}
	
	public void close()
	{
		persistentArrayFS.close();
	}
	
	private void readHeader() {
		byte[] buffer = persistentArrayFS.getHeader();
		header.deserialize(buffer);
	}

	private void writeHeader() {
		byte[] buffer = header.serialize();
		persistentArrayFS.putHeader(buffer);
	}
	
	public long getLength(){
		return header.getListSize();
	}
	public byte[] get(long index){
		return getNode(index).getData();
	}
	
	
	
	public void putHeader(byte[] buffer){
		header.setClientHeader(buffer);
		writeHeader();
	}
	public byte[] getHeader(){
		return header.getClientHeader();
	}
	
	public byte[] removeFromBack(){
		if(header.getListSize() <= 0)
			throw new RuntimeException("Nothing to remove");
		
		PersistentDoubleLinkListNode listHead = getListHead();
		long index = listHead.getFrontPointer();

		PersistentDoubleLinkListNode removedNode = removeNode(index);
		byte[] removedNodeBuffer = removedNode.getData();
		persistentArrayFS.deallocate(index);
		decrementListSize();
		return removedNodeBuffer;
	}

	private PersistentDoubleLinkListNode removeNode(long index) {
		PersistentDoubleLinkListNode node = getNode(index);
		long nodeBeforeIndex = node.getBackPointer();
		PersistentDoubleLinkListNode nodeBefore = getNode(nodeBeforeIndex);
		long nodeAfterIndex = node.getFrontPointer();
		PersistentDoubleLinkListNode nodeAfter = getNode(nodeAfterIndex);
		// this handles the special case for the list only containing two nodes
		if(nodeBeforeIndex == nodeAfterIndex) {
			nodeBefore = nodeAfter;
		}
		nodeBefore.setFrontPointer(nodeAfterIndex);
		nodeAfter.setBackPointer(nodeBeforeIndex);
		
		putNode(nodeBeforeIndex,nodeBefore);
		putNode(nodeAfterIndex,nodeAfter);
		return node;
	}

	public long addToFront(byte[] data){
		PersistentDoubleLinkListNode listHead = getListHead();
		long frontNodeIndex = listHead.getBackPointer();
		PersistentDoubleLinkListNode frontNode = getNode(frontNodeIndex); 
		PersistentDoubleLinkListNode newNode = 
				new PersistentDoubleLinkListNode(data,header.getListHeadIndex(),frontNodeIndex);
		
		long newNodeIndex = persistentArrayFS.allocate();
		if(frontNodeIndex == header.getListHeadIndex()) {
			frontNode = listHead;
		}
		frontNode.setFrontPointer(newNodeIndex);
		listHead.setBackPointer(newNodeIndex);
		
		putNode(frontNodeIndex,frontNode);
		putListHead(listHead);
		putNode(newNodeIndex,newNode);
		incrementListSize();
		
		return newNodeIndex;
	}

	private void putListHead(PersistentDoubleLinkListNode listHead) {
		putNode(header.getListHeadIndex(),listHead);
	}
	
	private PersistentDoubleLinkListNode getListHead() {
		long listHeadIndex = header.getListHeadIndex();
		PersistentDoubleLinkListNode listHead = getNode(listHeadIndex);
		return listHead;
	}
	public void moveToFront(long index){
		PersistentDoubleLinkListNode node = removeNode(index);
		
		PersistentDoubleLinkListNode listHead = getListHead();
		long listHeadIndex = header.getListHeadIndex();
		long frontNodeIndex = listHead.getBackPointer();
		PersistentDoubleLinkListNode frontNode = getNode(frontNodeIndex); 
		
		frontNode.setFrontPointer(index);
		listHead.setBackPointer(index);
		node.setFrontPointer(listHeadIndex);
		node.setBackPointer(frontNodeIndex);
		
		putNode(frontNodeIndex,frontNode);
		putListHead(listHead);
		putNode(index,node);
	}
	
	private void putNode(long index, PersistentDoubleLinkListNode node) {
		byte[] buffer = node.getSerializedNode();
		persistentArrayFS.put(index, buffer);
	}
	
	private PersistentDoubleLinkListNode getNode(long index) { 
		byte[] buffer = persistentArrayFS.get(index);
		return new PersistentDoubleLinkListNode(buffer);
	}

	private void incrementListSize(){
		header.incrementListSize();
		writeHeader();
	}
	
	private void decrementListSize(){
		header.decrementListSize();
		writeHeader();
	}

	
	public void printFile(PrintStream printStream){
		persistentArrayFS.printFile(printStream);
	}

	public void update(long index, byte[] value) {
		PersistentDoubleLinkListNode node = getNode(index);
		node.setData(value);
		putNode(index, node);
	}
	
	
	
	
	
	
	
	
	
}