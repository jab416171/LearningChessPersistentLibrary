package edu.neumont.learningChess.engine.persistence;

import java.nio.ByteBuffer;

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
	
	private void putLocalHeader() {
		persistentArrayFS.putHeader(header.serialize());
	}
	
	private void getLocalHeader() {
		header.deserialize(persistentArrayFS.getHeader());
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
	
	public void removeFromBack(){
		if(header.getListSize() <= 0)
			throw new RuntimeException("Nothing to remove");
		
		PersistentDoubleLinkListNode listHead = getListHead();
		long index = listHead.getFrontPointer();
		removeNode(index);
		persistentArrayFS.deallocate(index);
		decrementListSize();
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
	
	private void readHeader() {
		byte[] buffer = persistentArrayFS.getHeader();
		header.deserialize(buffer);
	}

	private void writeHeader() {
		byte[] buffer = header.serialize();
		persistentArrayFS.putHeader(buffer);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	
////	private long listSize;
////	private long headNodeIndex; 
////	private long maxListSize;
	
////	private static final long HEADER_SIZE = PersistentDoubleLinkListHeader.SIZE;
//	
//	

//	
//	
//	/**
//	 * 
//	 * @param toAddBuffer the buffer to be added to front of list
//	 * @param position of current buffer, pass in -1 if the node is new.
//	 * @return the actual position of the buffer in the array.
//	 */
//	public long addNodeToFront(byte[] toAddBuffer, long position ){
//		long headNodePosition = header.getListHeadIndex();
//		byte[] buffer = persistentArrayFS.get(headNodePosition);
//		PersistentDoubleLinkListNode headNode = new PersistentDoubleLinkListNode(buffer);
//		long oldFrontNodePosition = headNode.getFrontPointer();
//		buffer = persistentArrayFS.get(oldFrontNodePosition);
//		PersistentDoubleLinkListNode oldFrontNode = new PersistentDoubleLinkListNode(buffer);
//		long toAddNodePosition = position;
//		boolean increment = false;
//		if(toAddNodePosition == -1){
//			toAddNodePosition  = persistentArrayFS.allocate();
//			increment = true;
//		}
//		PersistentDoubleLinkListNode nodeToAdd 
//				= new PersistentDoubleLinkListNode(toAddBuffer, oldFrontNodePosition, headNodePosition);
//		if(headNodePosition == oldFrontNodePosition){
//			headNode.setBackPointer(toAddNodePosition);
//			headNode.setFrontPointer(toAddNodePosition);
//			buffer = headNode.getSerializedNode();
//			persistentArrayFS.put(headNodePosition, buffer );
//			persistentArrayFS.put(toAddNodePosition, nodeToAdd.getSerializedNode());
//		}
//		else{
//			oldFrontNode.setBackPointer(toAddNodePosition);
//			headNode.setFrontPointer(toAddNodePosition);
//			buffer = headNode.getSerializedNode();
//			persistentArrayFS.put(headNodePosition, buffer );
//			persistentArrayFS.put(toAddNodePosition, nodeToAdd.getSerializedNode());
//			persistentArrayFS.put(oldFrontNodePosition, oldFrontNode.getSerializedNode());
//		}
//		if(increment)
//			incrementListSize();
//		return toAddNodePosition;
//	}
//	
//	
//	public long getListLength()
//	{
//		return header.getListSize();
//	}
//	
//	public long getMaxListSize()
//	{
//		return header.getMaxListSize();
//	}
//	
////	public byte[] removeNode(long position)
////	{
////		PersistentDoubleLinkListNode currentNode 
////					= new PersistentDoubleLinkListNode(persistentArrayFS.get(position));
////		long oldFrontNodePosition = currentNode.getFrontPointer();
////		PersistentDoubleLinkListNode frontNode 
////					= new PersistentDoubleLinkListNode(persistentArrayFS.get(oldFrontNodePosition));
////		long backPosition = currentNode.getBackPointer();
////		PersistentDoubleLinkListNode backNode = new PersistentDoubleLinkListNode(persistentArrayFS.get(backPosition));
////		backNode.setFrontPointer(oldFrontNodePosition);
////		frontNode.setBackPointer(backPosition);
////		persistentArrayFS.deallocate(position);
////		decrementListSize();
////		persistentArrayFS.put(oldFrontNodePosition, backNode.getSerializedNode());
////		persistentArrayFS.put(backPosition, backNode.getSerializedNode());
////		return currentNode.getData();
////	}
//	
//	public byte[] getFrontNodeBuffer(){
//		byte[] buffer = persistentArrayFS.get(header.getListHeadIndex());
//		PersistentDoubleLinkListNode headNode = new PersistentDoubleLinkListNode(buffer);
//		long frontNodePosition = headNode.getFrontPointer();
//		PersistentDoubleLinkListNode frontNode = new PersistentDoubleLinkListNode(persistentArrayFS.get(frontNodePosition));
//		 return frontNode.getData();
//	}
//	
////	private byte[] getNode(long position)
////	{
////		return persistentArrayFS.get(position);
////	}
//	
////	public byte[] get(long position){
////		PersistentDoubleLinkListNode node = new PersistentDoubleLinkListNode(getNode(position));
////		return node.getData();
////	}
//	
//	public byte[] removeLastNode(){
//		PersistentDoubleLinkListHeader listHead = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
//		PersistentDoubleLinkListNode header = new PersistentDoubleLinkListNode(persistentArrayFS.get(listHead.getListHeadIndex()));
//		return removeNode(header.getBackPointer());
//	}
//
//	public byte[] getLastNodeBuffer() {
//		PersistentDoubleLinkListHeader listHead = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
//		PersistentDoubleLinkListNode header = new PersistentDoubleLinkListNode(persistentArrayFS.get(listHead.getListHeadIndex()));
//		return new PersistentDoubleLinkListNode(persistentArrayFS.get(header.getBackPointer())).getData();
//	}
//	
//	
	public void printFile(){
		persistentArrayFS.printFile();
	}
//	
}
