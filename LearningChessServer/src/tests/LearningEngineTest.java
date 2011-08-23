package tests;

import java.util.ArrayList;
import java.util.List;

import persistence.GameStateCache;
import server.csc385.Chess.ChessGame.model.Location;
import server.csc385.Chess.ChessGame.model.Move;
import server.csc385.Chess.ChessGame.model.Team;
import server.csc385.ChessGame.pieces.Bishop;
import server.csc385.ChessGame.pieces.ChessPiece;
import server.csc385.ChessGame.pieces.King;
import server.csc385.ChessGame.pieces.Knight;
import server.csc385.ChessGame.pieces.Pawn;
import server.csc385.ChessGame.pieces.Queen;
import server.csc385.ChessGame.pieces.Rook;
import ai.GameState;
import ai.GameStateHistory;
import ai.GameStateInfo;
import ai.LearningEngine;
import junit.framework.TestCase;

public class LearningEngineTest extends TestCase {
	private static final String FILENAME = "LExxxxx";
	
	public void testCreate(){
		
	}
	
	public void testOpen(){
		
	}
	
	public void testClose(){
		
	}
	
	
	public void testGetMove(){
		try{
			LearningEngine.delete(FILENAME);
		}
		catch(Throwable e)
		{}
		LearningEngine engine = null;
		LearningEngine.create(FILENAME, 32, 5);
		engine =  LearningEngine.open(FILENAME);
		PersistentGameStateCache cache = PersistentGameStateCache.open(FILENAME);
		GameState gsb4;
		GameState gsAfter;
		GameStateInfo gsif;
		gsb4 = setUpNormalBoard();
		gsAfter = setUpPwnBoard();
		gsif = new GameStateInfo(gsAfter.serialize(), 1, 1);
		cache.put(gsif);
		Move toTest =  new Move(new Location(1,0), new Location(2,0));
		Move toMove = null;
		try{
		toMove = engine.getMove(gsb4);
		}
		catch(Throwable e){
			e.printStackTrace();
		}
		assertTrue(toTest.getFrom().getRow() == toMove.getFrom().getRow() &&
				toTest.getFrom().getColumn() == toMove.getFrom().getColumn() &&
				toTest.getTo().getRow() == toMove.getTo().getRow() &&
				toTest.getTo().getColumn() == toMove.getTo().getColumn() 
		);
		cache.close();
		engine.close();
		LearningEngine.delete(FILENAME);
	}
	
	
	public void testAnalyzeGameHistory(){
		try{
			LearningEngine.delete(FILENAME);
		}
		catch(Throwable e)
		{}
		LearningEngine engine = null;
		LearningEngine.create(FILENAME, 32, 5);
		engine =  LearningEngine.open(FILENAME);
		PersistentGameStateCache cache = PersistentGameStateCache.open(FILENAME);
		assertTrue(cache.get(setUpBlack4MoveWinBoard()) == null);
		List<Move> moves = new ArrayList<Move>();
		moves.add(new Move(new Location(1,5), new Location(3,5)));
		moves.add(new Move(new Location(6,4), new Location(5,4)));
		moves.add(new Move(new Location(1,6), new Location(3,6)));
		moves.add(new Move(new Location(7,3), new Location(3,7)));
		GameStateHistory history = new GameStateHistory(moves);
		try{
		engine.analyzeGameHistory(history);
		}catch(Throwable e){
			e.printStackTrace();
			fail();
		}
		GameStateInfo info = cache.get(setUpBlack4MoveWinBoard());
		assertTrue(info.getAverage() == -1);
		assertTrue(info.getCount() == 1);
		engine.analyzeGameHistory(history);
		info =  cache.get(setUpBlack4MoveWinBoard());
		assertTrue(info.getAverage() == -1);
		assertTrue(info.getCount() == 2);
		cache.close();
		engine.close();
		LearningEngine.delete(FILENAME);
	}
	
	private GameState setUpNormalBoard() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		
		
		GameState gs = new GameState();
		// white teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(0, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(0, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(0, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(1, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// empty squares
		gs.setPiece(new Location(2, 0), null);
		gs.setPiece(new Location(2, 1), null);
		gs.setPiece(new Location(2, 2), null);
		gs.setPiece(new Location(2, 3), null);
		gs.setPiece(new Location(2, 4), null);
		gs.setPiece(new Location(2, 5), null);
		gs.setPiece(new Location(2, 6), null);
		gs.setPiece(new Location(2, 7), null);

		gs.setPiece(new Location(3, 0), null);
		gs.setPiece(new Location(3, 1), null);
		gs.setPiece(new Location(3, 2), null);
		gs.setPiece(new Location(3, 3), null);
		gs.setPiece(new Location(3, 4), null);
		gs.setPiece(new Location(3, 5), null);
		gs.setPiece(new Location(3, 6), null);
		gs.setPiece(new Location(3, 7), null);

		gs.setPiece(new Location(4, 0), null);
		gs.setPiece(new Location(4, 1), null);
		gs.setPiece(new Location(4, 2), null);
		gs.setPiece(new Location(4, 3), null);
		gs.setPiece(new Location(4, 4), null);
		gs.setPiece(new Location(4, 5), null);
		gs.setPiece(new Location(4, 6), null);
		gs.setPiece(new Location(4, 7), null);

		gs.setPiece(new Location(5, 0), null);
		gs.setPiece(new Location(5, 1), null);
		gs.setPiece(new Location(5, 2), null);
		gs.setPiece(new Location(5, 3), null);
		gs.setPiece(new Location(5, 4), null);
		gs.setPiece(new Location(5, 5), null);
		gs.setPiece(new Location(5, 6), null);
		gs.setPiece(new Location(5, 7), null);

		// black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 3));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		// black teams back row
		piece = new Rook();
		piece.setLocation(new Location(7, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(7, 3));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(7, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(7, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		gs.setRookMoved(0, false);
		gs.setRookMoved(1, false);
		gs.setRookMoved(2, false);
		gs.setRookMoved(3, false);
		gs.setKingMoved(true, false);
		gs.setKingMoved(false, false);
		gs.setTeamsMove(true);
		gs.setPawnMovedTwo(false);
		return gs;
	}
	
	private GameState setUpPwnBoard() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		
		
		GameState gs = new GameState();
		// white teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(0, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(0, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(0, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(2, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// empty squares
		gs.setPiece(new Location(1, 0), null);
		gs.setPiece(new Location(2, 1), null);
		gs.setPiece(new Location(2, 2), null);
		gs.setPiece(new Location(2, 3), null);
		gs.setPiece(new Location(2, 4), null);
		gs.setPiece(new Location(2, 5), null);
		gs.setPiece(new Location(2, 6), null);
		gs.setPiece(new Location(2, 7), null);

		gs.setPiece(new Location(3, 0), null);
		gs.setPiece(new Location(3, 1), null);
		gs.setPiece(new Location(3, 2), null);
		gs.setPiece(new Location(3, 3), null);
		gs.setPiece(new Location(3, 4), null);
		gs.setPiece(new Location(3, 5), null);
		gs.setPiece(new Location(3, 6), null);
		gs.setPiece(new Location(3, 7), null);

		gs.setPiece(new Location(4, 0), null);
		gs.setPiece(new Location(4, 1), null);
		gs.setPiece(new Location(4, 2), null);
		gs.setPiece(new Location(4, 3), null);
		gs.setPiece(new Location(4, 4), null);
		gs.setPiece(new Location(4, 5), null);
		gs.setPiece(new Location(4, 6), null);
		gs.setPiece(new Location(4, 7), null);

		gs.setPiece(new Location(5, 0), null);
		gs.setPiece(new Location(5, 1), null);
		gs.setPiece(new Location(5, 2), null);
		gs.setPiece(new Location(5, 3), null);
		gs.setPiece(new Location(5, 4), null);
		gs.setPiece(new Location(5, 5), null);
		gs.setPiece(new Location(5, 6), null);
		gs.setPiece(new Location(5, 7), null);

		// black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 3));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		// black teams back row
		piece = new Rook();
		piece.setLocation(new Location(7, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(7, 3));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(7, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(7, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		gs.setRookMoved(0, false);
		gs.setRookMoved(1, false);
		gs.setRookMoved(2, false);
		gs.setRookMoved(3, false);
		gs.setKingMoved(true, false);
		gs.setKingMoved(false, false);
		gs.setTeamsMove(true);
		gs.setPawnMovedTwo(false);
		return gs;
	}
	
	private GameState setUpBlack4MoveWinBoard() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		
		
		GameState gs = new GameState();
		// white teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(0, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(0, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(0, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(0, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(0, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(1, 0));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 1));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 2));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 3));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 4));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(3, 5));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(3, 6));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(1, 7));
		piece.setTeam(white);
		gs.setPiece(piece.getLocation(), piece);

		// empty squares
		gs.setPiece(new Location(2, 0), null);
		gs.setPiece(new Location(2, 1), null);
		gs.setPiece(new Location(2, 2), null);
		gs.setPiece(new Location(2, 3), null);
		gs.setPiece(new Location(2, 4), null);
		gs.setPiece(new Location(2, 5), null);
		gs.setPiece(new Location(2, 6), null);
		gs.setPiece(new Location(2, 7), null);

		gs.setPiece(new Location(3, 0), null);
		gs.setPiece(new Location(3, 1), null);
		gs.setPiece(new Location(3, 2), null);
		gs.setPiece(new Location(3, 3), null);
		gs.setPiece(new Location(3, 4), null);
		gs.setPiece(new Location(1, 5), null);
		gs.setPiece(new Location(1, 6), null);
		gs.setPiece(new Location(7, 3), null);

		gs.setPiece(new Location(4, 0), null);
		gs.setPiece(new Location(4, 1), null);
		gs.setPiece(new Location(4, 2), null);
		gs.setPiece(new Location(4, 3), null);
		gs.setPiece(new Location(4, 4), null);
		gs.setPiece(new Location(4, 5), null);
		gs.setPiece(new Location(4, 6), null);
		gs.setPiece(new Location(4, 7), null);

		gs.setPiece(new Location(5, 0), null);
		gs.setPiece(new Location(5, 1), null);
		gs.setPiece(new Location(5, 2), null);
		gs.setPiece(new Location(5, 3), null);
		gs.setPiece(new Location(6, 4), null);
		gs.setPiece(new Location(5, 5), null);
		gs.setPiece(new Location(5, 6), null);
		gs.setPiece(new Location(5, 7), null);

		// black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 3));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(5, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Pawn();
		piece.setLocation(new Location(6, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		// black teams back row
		piece = new Rook();
		piece.setLocation(new Location(7, 0));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 1));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 2));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Queen();
		piece.setLocation(new Location(3, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new King();
		piece.setLocation(new Location(7, 4));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Bishop();
		piece.setLocation(new Location(7, 5));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Knight();
		piece.setLocation(new Location(7, 6));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		piece = new Rook();
		piece.setLocation(new Location(7, 7));
		piece.setTeam(black);
		gs.setPiece(piece.getLocation(), piece);

		gs.setRookMoved(0, false);
		gs.setRookMoved(1, false);
		gs.setRookMoved(2, false);
		gs.setRookMoved(3, false);
		gs.setKingMoved(true, false);
		gs.setKingMoved(false, false);
		gs.setTeamsMove(true);
		gs.setPawnMovedTwo(false);
		return gs;
	}
}
