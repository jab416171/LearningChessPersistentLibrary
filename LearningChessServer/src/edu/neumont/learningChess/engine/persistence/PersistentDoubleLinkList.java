package edu.neumont.learningChess.engine.persistence;


public class PersistentDoubleLinkList {

	private PersistentArrayWithFreeSpace persistentArrayFS;
//	private long listSize;
//	private long headNodeIndex;
//	private long maxListSize;
	private static final long LONG_SIZE = 8;
	private static final long HEADER_SIZE = PersistentDoubleLinkListHeader.SIZE;
	private PersistentDoubleLinkListHeader header;
	
	private PersistentDoubleLinkList(String linkListName)
	{
		this.persistentArrayFS = PersistentArrayWithFreeSpace.open(linkListName);
		header = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
//		listSize = listHead.getListSize();
//		headNodeIndex = listHead.getIndex();
//		maxListSize = listHead.getMaxListSize();
	}
	
	private void readHeader() {
		byte[] buffer = persistentArrayFS.getHeader();
		header.deserialize(buffer);
	}
	
	private void writeHeader() {
		byte[] buffer = header.serialize();
		persistentArrayFS.putHeader(buffer);
	}
	
	public static void create(String fileName, long recordSize, long maxListSize)
	{
		PersistentArrayWithFreeSpace tempArrayWtihFreeSpace = null;
		try {
			PersistentArrayWithFreeSpace.create(fileName, recordSize + (2 * LONG_SIZE), HEADER_SIZE, new byte[(int) HEADER_SIZE]);
			tempArrayWtihFreeSpace = PersistentArrayWithFreeSpace.open(fileName);
			long headNodePosition = tempArrayWtihFreeSpace.allocate();
			PersistentDoubleLinkListNode node = 
					new PersistentDoubleLinkListNode(new byte[(int)recordSize], headNodePosition, headNodePosition);
			tempArrayWtihFreeSpace.put(headNodePosition, node.getSerializedNode() );
			tempArrayWtihFreeSpace.putHeader(
					new PersistentDoubleLinkListHeader(headNodePosition, 0, maxListSize).serialize());
			tempArrayWtihFreeSpace.close();
		}catch(Throwable e){
			if(tempArrayWtihFreeSpace != null)
				tempArrayWtihFreeSpace.close();
			throw new RuntimeException("The linked list, " + fileName + ", could not be created.", e);
		}
	}
	
	public static void delete(String linkListName)
	{
		try{
			PersistentArray.delete(linkListName);
		}catch (Throwable e){
			throw new RuntimeException("The linked list, " + linkListName + ", could not be deleted.",e);
		}
	}
	
	public static PersistentDoubleLinkList open(String linkListName)
	{
		PersistentDoubleLinkList toOpen = null;
		try{
			toOpen = new PersistentDoubleLinkList(linkListName);
		}catch (Throwable e ){
			throw new RuntimeException("The linked list, " + linkListName + ", could not be opened.",e);
		}
		return toOpen;
	}
	
	
	public void close()
	{
		persistentArrayFS.close();
	}

	/**
	 * 
	 * @param toAddBuffer the buffer to be added to front of list
	 * @param position of current buffer, pass in -1 if the node is new.
	 * @return the actual position of the buffer in the array.
	 */
	public long addNodeToFront(byte[] toAddBuffer, long position ){
		long headNodePosition = header.getListHeadIndex();
		byte[] buffer = persistentArrayFS.get(headNodePosition);
		PersistentDoubleLinkListNode headNode = new PersistentDoubleLinkListNode(buffer);
		long oldFrontNodePosition = headNode.getFrontPointer();
		buffer = persistentArrayFS.get(oldFrontNodePosition);
		PersistentDoubleLinkListNode oldFrontNode = new PersistentDoubleLinkListNode(buffer);
		long toAddNodePosition = position;
		boolean increment = false;
		if(toAddNodePosition == -1){
			toAddNodePosition  = persistentArrayFS.allocate();
			increment = true;
		}
		PersistentDoubleLinkListNode nodeToAdd 
				= new PersistentDoubleLinkListNode(toAddBuffer, oldFrontNodePosition, headNodePosition);
		if(headNodePosition == oldFrontNodePosition){
			headNode.setBackPointer(toAddNodePosition);
			headNode.setFrontPointer(toAddNodePosition);
			buffer = headNode.getSerializedNode();
			persistentArrayFS.put(headNodePosition, buffer );
			persistentArrayFS.put(toAddNodePosition, nodeToAdd.getSerializedNode());
		}
		else{
			oldFrontNode.setBackPointer(toAddNodePosition);
			headNode.setFrontPointer(toAddNodePosition);
			buffer = headNode.getSerializedNode();
			persistentArrayFS.put(headNodePosition, buffer );
			persistentArrayFS.put(toAddNodePosition, nodeToAdd.getSerializedNode());
			persistentArrayFS.put(oldFrontNodePosition, oldFrontNode.getSerializedNode());
		}
		if(increment)
			incrementListSize();
		return toAddNodePosition;
	}
	
	
	private void incrementListSize(){
		header.incrementListSize();
		writeHeader();
	}
	
	private void decrementListSize(){
		header.decrementListSize();
		writeHeader();
	}
	
	public long getListLength()
	{
		return header.getListSize();
	}
	
	public long getMaxListSize()
	{
		return header.getMaxListSize();
	}
	
	public byte[] removeNode(long position)
	{
		PersistentDoubleLinkListNode currentNode 
					= new PersistentDoubleLinkListNode(persistentArrayFS.get(position));
		long oldFrontNodePosition = currentNode.getFrontPointer();
		PersistentDoubleLinkListNode frontNode 
					= new PersistentDoubleLinkListNode(persistentArrayFS.get(oldFrontNodePosition));
		long backPosition = currentNode.getBackPointer();
		PersistentDoubleLinkListNode backNode = new PersistentDoubleLinkListNode(persistentArrayFS.get(backPosition));
		backNode.setFrontPointer(oldFrontNodePosition);
		frontNode.setBackPointer(backPosition);
		persistentArrayFS.deallocate(position);
		decrementListSize();
		persistentArrayFS.put(oldFrontNodePosition, backNode.getSerializedNode());
		persistentArrayFS.put(backPosition, backNode.getSerializedNode());
		return currentNode.getData();
	}
	
	public byte[] getFrontNodeBuffer(){
		byte[] buffer = persistentArrayFS.get(header.getListHeadIndex());
		PersistentDoubleLinkListNode headNode = new PersistentDoubleLinkListNode(buffer);
		long frontNodePosition = headNode.getFrontPointer();
		PersistentDoubleLinkListNode frontNode = new PersistentDoubleLinkListNode(persistentArrayFS.get(frontNodePosition));
		 return frontNode.getData();
	}
	
	private byte[] getNode(long position)
	{
		return persistentArrayFS.get(position);
	}
	
	public byte[] get(long position){
		PersistentDoubleLinkListNode node = new PersistentDoubleLinkListNode(getNode(position));
		return node.getData();
	}
	
	public byte[] removeLastNode(){
		PersistentDoubleLinkListHeader listHead = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
		PersistentDoubleLinkListNode header = new PersistentDoubleLinkListNode(persistentArrayFS.get(listHead.getListHeadIndex()));
		return removeNode(header.getBackPointer());
	}

	public byte[] getLastNodeBuffer() {
		PersistentDoubleLinkListHeader listHead = new PersistentDoubleLinkListHeader(persistentArrayFS.getHeader());
		PersistentDoubleLinkListNode header = new PersistentDoubleLinkListNode(persistentArrayFS.get(listHead.getListHeadIndex()));
		return new PersistentDoubleLinkListNode(persistentArrayFS.get(header.getBackPointer())).getData();
	}
	
	
	public void printFile(){
		persistentArrayFS.printFile();
	}
	
}
