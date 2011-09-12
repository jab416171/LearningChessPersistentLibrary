package edu.neumont.learningChess.engine;

import java.nio.ByteBuffer;

public class GameStateInfo {
	
	private float average, count;
	private static final int FLOAT_SIZE = 4;

	public GameStateInfo(byte[] buffer) {
		deserialize(buffer);
	}
	
	public GameStateInfo(float average, float count) {
		this.average = average;
		this.count = count;
	}

	public void addObservation(float observation) {
		float tempValue = average * count;
		tempValue = tempValue + observation;
		count++;
		average = tempValue/count;
	}
	
	public byte[] serialize() {
		byte[] buffer = new byte[(2*FLOAT_SIZE)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putFloat(average);
		byteBuffer.putFloat(count);
		return buffer;
	}
	
	private void deserialize(byte[] buffer) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		average = byteBuffer.getFloat();
		count = byteBuffer.getFloat();
	}

	public float getAverage() {
		return average;
	}

	public float getCount() {
		return count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(average);
		result = prime * result + Float.floatToIntBits(count);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GameStateInfo other = (GameStateInfo) obj;
		if (Float.floatToIntBits(average) != Float.floatToIntBits(other.average)) {
			return false;
		}
		if (Float.floatToIntBits(count) != Float.floatToIntBits(other.count)) {
			return false;
		}
		return true;
	}
	
}
