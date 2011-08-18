package edu.neumont.learningChess.engine;

import java.nio.ByteBuffer;

import edu.neumont.learningChess.model.Team;


public class GameStateInfo {
	
	private byte[] gameStateBuffer;
	private byte[] serializedGameStateInfo;
	private float average, count;
	private static final int FLOAT_SIZE = 4;

	public GameStateInfo(byte[] buffer){
		serializedGameStateInfo = buffer;
		deserialize();
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
	
	private void serialize(){
		serializedGameStateInfo = new byte[gameStateBuffer.length + (2*FLOAT_SIZE)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedGameStateInfo);
		byteBuffer.putFloat(average);
		byteBuffer.putFloat(count);
		byteBuffer.put(gameStateBuffer);
	}
	
	private void deserialize() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(serializedGameStateInfo);
		average = byteBuffer.getFloat();
		count = byteBuffer.getFloat();
		gameStateBuffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(gameStateBuffer, 0, byteBuffer.remaining());
	}


	public GameState getGameState() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		return new GameState(gameStateBuffer, white, black);
	}
	
	public byte[] getGameStateBuffer(){
		return gameStateBuffer;
	}

	public byte[] getSerializedGameStateInfo() {
		return serializedGameStateInfo;
	}

	public float getAverage() {
		return average;
	}

	public float getCount() {
		return count;
	}
	
}
