package edu.neumont.learningChess.engine;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import edu.neumont.learningChess.api.ExtendedMove;

public class GameStateHistory implements Enumeration<ExtendedMove>{
	//Holds a list of game states for a single finished game
	
	private List<ExtendedMove> ExtendedMoves;
	private Iterator<ExtendedMove> iter;
	
	public GameStateHistory(List<ExtendedMove> ExtendedMoves) {
		this.ExtendedMoves = ExtendedMoves;
		iter = this.ExtendedMoves.iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return iter.hasNext();
	}

	@Override
	public ExtendedMove nextElement() {
		return iter.next();
	}
}
