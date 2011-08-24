package edu.neumont.learningChess.engine;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import edu.neumont.chessModel.movement.Move;

public class GameStateHistory implements Enumeration<Move>{
	//Holds a list of game states for a single finished game
	
	private List<Move> moves;
	private Iterator<Move> iter;
	
	public GameStateHistory(List<Move> moves) {
		this.moves = moves;
		iter = this.moves.iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return iter.hasNext();
	}

	@Override
	public Move nextElement() {
		return iter.next();
	}
}
