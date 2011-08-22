package edu.neumont.learningChess.engine.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PersistentArray {
	private static final long HEADER_OFFSET = 0;
	private RandomAccessFile arrayStream;
	private PersistentArrayHeader header;

	private PersistentArray(String fileName){
		try {
			arrayStream = new RandomAccessFile(fileName, "rwd");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("The file, " + fileName + ", does not exist", e);
		}
	}
	
	private void readHeader() {
		try {
			arrayStream.seek(HEADER_OFFSET);
			header = PersistentArrayHeader.read(arrayStream);
		} catch (IOException e) {
			throw new RuntimeException("Failure to read header.", e);
		}
	}
	
	private void writeHeader() {
		try {
			arrayStream.seek(HEADER_OFFSET);
			header.write(arrayStream);
		} catch (IOException e) {
			throw new RuntimeException("Failure to write header.", e);
		}
		
	}
	
//	private void readHeaderSize()  {
//		try {
//			arrayStream.seek(HEADER_SIZE_OFFSET);
//			headerSize = arrayStream.readLong();
//		} catch (IOException e) {
//			headerSize = 0;
//		}
//	}
//
//	private void writeHeaderSize() {
//		try {
//			arrayStream.seek(HEADER_SIZE_OFFSET);
//			arrayStream.writeLong(headerSize);
//		} catch (IOException e) {
//			throw new RuntimeException("Failure to write size of header.", e);
//		}
//	}

	public static void create(String fileName, long recordSize, long headerSize){
		if(fileName.isEmpty() || fileName == null){
			throw new RuntimeException("Invalid file name: " + fileName + ".");
		}
		if(recordSize < 0 || recordSize > Long.MAX_VALUE){
			throw new RuntimeException("Invalid size of buffer: " + recordSize + ".(Must be greater than "+0+")");
		}
		File fileArray = new File(fileName);
		if(fileArray.exists()){
			throw new RuntimeException("Cannot create existing file - "  + fileName + ".");
		}
		try {
			fileArray.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException("The file, " + fileName +", cannot be created", e);
		}
		PersistentArray persistentArray = new PersistentArray(fileName);
		persistentArray.header = new PersistentArrayHeader(recordSize,headerSize);
		persistentArray.writeHeader();
		persistentArray.close();
	}
	
	public static void delete(String fileName){
		File fileArray = new File(fileName);
		if(fileArray.exists()){
			if(!fileArray.delete()) {
				throw new RuntimeException("The file, with file name " + fileName + ", failed to be deleted. (The stream might not be closed)");
			}
		}
		else{
			throw new RuntimeException("There is no file, with file name " + fileName + ", to delete.");
		}
	}
	
	public static PersistentArray open(String fileName) {
		File fileArray = new File(fileName);
		PersistentArray tempPersistArray = null;
		if( fileArray.exists()){
			tempPersistArray = new PersistentArray(fileName);
			tempPersistArray.readHeader();
		}
		else{
			throw new RuntimeException("The file, " + fileName + ", cannot be opened, or does not exist.");
		}
		return tempPersistArray;
	}
	
	public void close(){
		try {
			arrayStream.close();
		} catch (IOException e) {
			throw new RuntimeException("The RandomAccessFile cannot be closed.", e);
		}
	}
	
	
	public void putHeader (byte[] buffer){
		if(buffer.length != header.getClientHeaderSize())
			throw new RuntimeException("Header file size mismatch, buffer length is " + buffer.length + ", clientHeaderSize is " + header.getClientHeaderSize());
		header.setClientHeader(buffer);
		writeHeader();
	}
	
	public byte[] getHeader(){
		return header.getClientHeader();
	}
	
	public void put (long index, byte[] buffer){
		if(buffer.length > header.getClientRecordSize())
			throw new RuntimeException("The buffer being put is larger than record size. Size mismatch error.");
		if(index >= (Long.MAX_VALUE - 1) || index < 0)
			throw new RuntimeException("Put location error, index was " + index);
		try {
			long offset = getOffset(index);
			arrayStream.seek(offset);
			arrayStream.write(buffer, 0, buffer.length);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
	}
	
	private long getMaxIndex() {
		return (Long.MAX_VALUE - header.getHeaderSize()) / header.getClientRecordSize();
	}
	
	public byte[] get(long index){
		if(index >= getMaxIndex() || index < 0)
			throw new RuntimeException("Trying to get something from beyond file: " + index + ".");
		
		byte[] buffer = new byte[(int)header.getClientRecordSize()];
		try {
			long offset = getOffset(index);
			arrayStream.seek(offset);
			// end of file will keep the buffer empty
			arrayStream.read(buffer);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		return buffer;
	}
	
	public long count(){
		long length = 0;
		try {
			length = arrayStream.length();
			
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		return ((length - header.getHeaderSize()) / header.getClientRecordSize());
	}
	
	
	
	public void printFile(){

		System.out.println("Start print file----------------");
		try {
			arrayStream.seek(0);
			for (int i = 0; i < arrayStream.length(); i++) {
				System.out.println( i + ": " + arrayStream.read());
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("end print file----------------");
	}
	
	
	private long getOffset(long index){
		return header.getHeaderSize() + (index * header.getClientRecordSize());
	}

	public void remove(long index) {
		byte[] buffer = new byte[(int)header.getClientRecordSize()];
		this.put(index, buffer);
	}
	
	public long getRecordSize(){
		return header.getClientRecordSize();
	}
	
}