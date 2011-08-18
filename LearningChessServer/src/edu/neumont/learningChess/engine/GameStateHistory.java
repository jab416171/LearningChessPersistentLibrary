package edu.neumont.learningChess.engine;

import java.util.List;

import edu.neumont.learningChess.model.Move;

public class GameStateHistory {
	//Holds a list of game states for a single fininished game
	
	List<Move> moves;
	
	public GameStateHistory(List<Move> moves) {
		this.moves = moves;
	}
	
	public List<Move> getMoves()
	{
		return moves;
	}
}
