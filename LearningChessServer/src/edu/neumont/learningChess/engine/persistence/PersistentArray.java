package edu.neumont.learningChess.engine.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PersistentArray {
	private static final long LONG_SIZE = 8;
	private static final long RECORD_SIZE_OFFSET = 0;
	private static final long HEADER_SIZE_OFFSET = RECORD_SIZE_OFFSET + LONG_SIZE;
	private static final long USER_HEADER_OFFSET = HEADER_SIZE_OFFSET + LONG_SIZE;
	private RandomAccessFile arrayStream;
	private long recordSize;
	private long headerSize;
	
	public long getRecordSize(){
		return recordSize;
	}
	
	private void readRecordSize()  {
		try {
			arrayStream.seek(RECORD_SIZE_OFFSET);
			recordSize = arrayStream.readLong();
		} catch (IOException e) {
			recordSize = 0;
		}
	}

	private void writeRecordSize() {
		try {
			arrayStream.seek(RECORD_SIZE_OFFSET);
			arrayStream.writeLong(recordSize);
		} catch (IOException e) {
			throw new RuntimeException("Failure to write size.", e);
		}
	}
	
	private void readHeaderSize()  {
		try {
			arrayStream.seek(HEADER_SIZE_OFFSET);
			headerSize = arrayStream.readLong();
		} catch (IOException e) {
			headerSize = 0;
		}
	}

	private void writeHeaderSize() {
		try {
			arrayStream.seek(HEADER_SIZE_OFFSET);
			arrayStream.writeLong(headerSize);
		} catch (IOException e) {
			throw new RuntimeException("Failure to write size of header.", e);
		}
	}

	public static void create(String fileName, long recordSize, long headerSize){
		if(fileName.isEmpty() || fileName == null){
			throw new RuntimeException("Invalid file name: " + fileName + ".");
		}
		if(recordSize < 8 || recordSize > Long.MAX_VALUE){
			throw new RuntimeException("Invlaid size of buffer: " + recordSize + ".(Must be greater than 8)");
		}
		File fileArray = new File(fileName);
		if(fileArray.exists()){
			throw new RuntimeException("Cannot create existing file - "  + fileName + ".");
		}
		else{
			try {
				fileArray.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("The file, " + fileName +", cannot be created", e);
			}
			PersistentArray persistantArray = new PersistentArray(fileName);
			persistantArray.recordSize = recordSize;
			persistantArray.headerSize = headerSize;
			persistantArray.writeRecordSize();
			persistantArray.writeHeaderSize();
			persistantArray.close();
		}
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
	
	private PersistentArray(String fileName){
		try {
			arrayStream = new RandomAccessFile(fileName, "rwd");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("The file, " + fileName + ", does not exist", e);
		}
		readRecordSize();
		readHeaderSize();
	}
	
	public void putHeader (byte[] buffer){
		if(buffer.length != headerSize)
			throw new RuntimeException("Header file size mismatch");
		try {
			arrayStream.seek(USER_HEADER_OFFSET);
			arrayStream.write(buffer, 0, (int)headerSize);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
	}
	
	public byte[] getHeader(){
		byte[] buffer = null;
		long temp = headerSize;
		try {
			if(temp == -1)
				temp = arrayStream.length() - (2*LONG_SIZE);
			buffer = new byte[(int)temp];
			arrayStream.seek(USER_HEADER_OFFSET);
			arrayStream.read(buffer);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		return buffer;
	}
	
	public void put (long n, byte[] buffer){
		if(buffer.length > recordSize)
			throw new RuntimeException("The buffer being put is larger than record size. Size mismatch error.");
		if(n >= (Long.MAX_VALUE - 1) || n < 0)
			throw new RuntimeException("Put location error.");
		try {
			long offset = getOffset(n);
			arrayStream.seek(offset);
			arrayStream.write(buffer, 0, buffer.length);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
	}
	
	public byte[] get(long n){
		int response = 0;
		byte[] buffer = new byte[(int)recordSize];
		if(n > (Long.MAX_VALUE - recordSize) || n < 0)
			throw new RuntimeException("Trying to get something from beyond file: " + n + ".");
		try {
			long offset = getOffset(n);
			arrayStream.seek(offset);
			response = arrayStream.read(buffer);
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		if(response == -1)// || Arrays.equals(buffer,new byte[buffer.length]))
			buffer = null;
		return buffer;
	}
	
	public long count(){
		long length = 0;
		try {
			length = arrayStream.length();
			
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		return ((length - USER_HEADER_OFFSET - headerSize)/recordSize);
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
	
	
	public long length(){
		long length = 0;
		try {
			length = arrayStream.length();
			
		} catch (IOException e) {
			throw new RuntimeException("IO error.", e);
		}
		return length;
	}
	
	private long getOffset(long n){
		return USER_HEADER_OFFSET + headerSize + (n * recordSize);
	}

	public void remove(long code) {
		byte[] buffer = new byte[(int)recordSize];
		this.put(code, buffer);
	}
}