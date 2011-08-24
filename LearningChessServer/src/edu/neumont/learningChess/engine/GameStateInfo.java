package edu.neumont.learningChess.engine;

import java.nio.ByteBuffer;


public class GameStateInfo {
	
	private byte[] gameStateBuffer;
	private float average, count;
	private static final int FLOAT_SIZE = 4;

	public GameStateInfo(byte[] buffer){
		deserialize(buffer);
	}
	
	public GameStateInfo(byte[] gamestate, float average, float count ) 
	{
		gameStateBuffer = gamestate;
		this.average = average;
		this.count = count;
		serialize();
	}

	public void addObservation(float observation)
	{
		float tempValue = average * count;
		tempValue = tempValue + observation;
		count++;
		average = tempValue/count;
		serialize();
	}
	
	private byte[] serialize(){
		byte[] buffer = new byte[gameStateBuffer.length + (2*FLOAT_SIZE)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putFloat(average);
		byteBuffer.putFloat(count);
		byteBuffer.put(gameStateBuffer);
		return buffer;
	}
	
	private void deserialize(byte[] buffer) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		average = byteBuffer.getFloat();
		count = byteBuffer.getFloat();
		gameStateBuffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(gameStateBuffer, 0, byteBuffer.remaining());
	}


//	public GameState getGameState() {
//		Team white = new Team(Team.Color.LIGHT);
//		Team black = new Team(Team.Color.DARK);
//		return new GameState(gameStateBuffer, white, black);
//	}
	
	public byte[] getGameStateBuffer(){
		return gameStateBuffer;
	}

	public byte[] getSerializedGameStateInfo() {
		return serialize();
	}

	public float getAverage() {
		return average;
	}

	public float getCount() {
		return count;
	}
	
}
