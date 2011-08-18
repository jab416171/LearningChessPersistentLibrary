package tests;

import server.csc385.Chess.ChessGame.model.Location;
import server.csc385.Chess.ChessGame.model.Team;
import server.csc385.ChessGame.pieces.Bishop;
import server.csc385.ChessGame.pieces.ChessPiece;
import server.csc385.ChessGame.pieces.King;
import server.csc385.ChessGame.pieces.Knight;
import server.csc385.ChessGame.pieces.Pawn;
import server.csc385.ChessGame.pieces.Queen;
import server.csc385.ChessGame.pieces.Rook;
import ai.GameState;
import ai.NibbleBoard;
import junit.framework.TestCase;

public class NibbleBoardTest extends TestCase {

	byte[] blankGameState = null;
	byte[] PiecesEveryWhereGameState = null;
	
	public void createStartingBoard()
	{
		GameState gs = new GameState();	
		//black teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0,0)); 
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,1));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,2));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Queen();
		piece.setLocation(new Location(0,3));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new King();
		piece.setLocation(new Location(0,4));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,5));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,6));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Rook();
		piece.setLocation(new Location(0,7));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		//black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(1,0));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,1));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,2));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,3));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,4));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,5));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,6));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,7));
		piece.setTeam(new Team(Team.Color.DARK));
		gs.setPiece(piece.getLocation(), piece);
		
		//empty squares
		gs.setPiece(new Location(2,0), null);
		gs.setPiece(new Location(2,1), null);
		gs.setPiece(new Location(2,2), null);
		gs.setPiece(new Location(2,3), null);
		gs.setPiece(new Location(2,4), null);
		gs.setPiece(new Location(2,5), null);
		gs.setPiece(new Location(2,6), null);
		gs.setPiece(new Location(2,7), null);
		
		gs.setPiece(new Location(3,0), null);
		gs.setPiece(new Location(3,1), null);
		gs.setPiece(new Location(3,2), null);
		gs.setPiece(new Location(3,3), null);
		gs.setPiece(new Location(3,4), null);
		gs.setPiece(new Location(3,5), null);
		gs.setPiece(new Location(3,6), null);
		gs.setPiece(new Location(3,7), null);
		
		gs.setPiece(new Location(4,0), null);
		gs.setPiece(new Location(4,1), null);
		gs.setPiece(new Location(4,2), null);
		gs.setPiece(new Location(4,3), null);
		gs.setPiece(new Location(4,4), null);
		gs.setPiece(new Location(4,5), null);
		gs.setPiece(new Location(4,6), null);
		gs.setPiece(new Location(4,7), null);
		
		gs.setPiece(new Location(5,0), null);
		gs.setPiece(new Location(5,1), null);
		gs.setPiece(new Location(5,2), null);
		gs.setPiece(new Location(5,3), null);
		gs.setPiece(new Location(5,4), null);
		gs.setPiece(new Location(5,5), null);
		gs.setPiece(new Location(5,6), null);
		gs.setPiece(new Location(5,7), null);
		
		//white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		//white teams back row
		piece = new Rook();
		piece.setLocation(new Location(7,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Queen();
		piece.setLocation(new Location(7,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new King();
		piece.setLocation(new Location(7,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		piece = new Rook();
		piece.setLocation(new Location(7,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		gs.setPiece(piece.getLocation(), piece);
		
		gs.setRookMoved(0, false);
		gs.setRookMoved(1, false);
		gs.setRookMoved(2, false);
		gs.setRookMoved(3, false);
		gs.setKingMoved(true, false);
		gs.setKingMoved(false, false);
		gs.setTeamsMove(true);
		gs.setPawnMovedTwo(false);
		blankGameState = gs.serialize();
	}
	
	public void createPiecesEverywhereBoard()
	{
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		
		GameState gState = new GameState();	
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0,0));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(0,1), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,2));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Queen();
		piece.setLocation(new Location(0,3));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new King();
		piece.setLocation(new Location(0,4));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(0,5), null);
		
		piece = new Knight();
		piece.setLocation(new Location(0,6));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Rook();
		piece.setLocation(new Location(0,7));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		

		piece = new Pawn();
		piece.setLocation(new Location(1,0));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(1,1), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,2));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,3));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(1,4), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,5));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,6));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,7));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(2,0), null);
		gState.setPiece(new Location(2,1), null);
		piece = new Knight();
		piece.setLocation(new Location(2,2));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(2,3), null);
		piece = new Pawn();
		piece.setLocation(new Location(2,4));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(2,5), null);
		gState.setPiece(new Location(2,6), null);
		piece = new Bishop();
		piece.setLocation(new Location(2,7));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece); 
		
		gState.setPiece(new Location(3,0), null);
		piece = new Pawn();
		piece.setLocation(new Location(3,1));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(3,2), null);
		gState.setPiece(new Location(3,3), null);
		gState.setPiece(new Location(3,4), null);
		piece = new Queen();
		piece.setLocation(new Location(3,5));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(3,6), null);
		gState.setPiece(new Location(3,7), null);
		
		gState.setPiece(new Location(4,0), null);
		piece = new Pawn();
		piece.setLocation(new Location(4,1));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(4,2), null);
		piece = new Pawn();
		piece.setLocation(new Location(4,3));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(4,4), null);
		gState.setPiece(new Location(4,5), null);
		gState.setPiece(new Location(4,6), null);
		piece = new Rook();
		piece.setLocation(new Location(4,7));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(5,0), null);
		gState.setPiece(new Location(5,1), null);
		piece = new Knight();
		piece.setLocation(new Location(5,2));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(5,3), null);
		gState.setPiece(new Location(5,4), null);
		piece = new Pawn();
		piece.setLocation(new Location(5,5));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		gState.setPiece(new Location(5,6), null);
		gState.setPiece(new Location(5,7), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,0));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(6,1), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,2));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new King();
		piece.setLocation(new Location(6,3));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,4));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(6,5), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,6));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,7));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Rook();
		piece.setLocation(new Location(7,0));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(7,1), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,2));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(7,3), null);
		
		gState.setPiece(new Location(7,4), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,5));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,6));
		piece.setTeam(white);
		gState.setPiece(piece.getLocation(), piece);
		
		gState.setPiece(new Location(7,7), null);
		gState.setRookMoved(0, true);
		gState.setRookMoved(1, false);
		gState.setRookMoved(2, false);
		gState.setRookMoved(3, true);
		gState.setKingMoved(true, true);
		gState.setKingMoved(false, false);
		gState.setTeamsMove(false);
		gState.setPawnMovedTwo(true);
		gState.setPawnMovedTwoRow(4);
		gState.setPawnMovedTwoCol(5);
		PiecesEveryWhereGameState = gState.serialize();
	}
	
	public void testNibbleBoard() {
		createStartingBoard();
		NibbleBoard board = new NibbleBoard(blankGameState);
		assertEquals(7, board.get(0, 0));
		board.set(0, 0, 12);
		assertEquals(12, board.get(0, 0));
		createPiecesEverywhereBoard();
		board = new NibbleBoard(PiecesEveryWhereGameState);
		assertEquals(6, board.get(0, 0));
		board.set(0, 0, 12);
		assertEquals(12, board.get(0, 0));
	}

	public void testGet() {
		createStartingBoard();
		NibbleBoard board = new NibbleBoard(blankGameState);
		assertEquals(7, board.get(0, 0));
		assertEquals(9, board.get(0, 1));
		assertEquals(11, board.get(0, 2));
		assertEquals(13, board.get(0, 3));
		assertEquals(15, board.get(0, 4));
		assertEquals(11, board.get(0, 5));
		assertEquals(9, board.get(0, 6));
		assertEquals(7, board.get(0, 7));
		
		assertEquals(5, board.get(1, 0));
		assertEquals(5, board.get(1, 1));
		assertEquals(5, board.get(1, 2));
		assertEquals(5, board.get(1, 3));
		assertEquals(5, board.get(1, 4));
		assertEquals(5, board.get(1, 5));
		assertEquals(5, board.get(1, 6));
		assertEquals(5, board.get(1, 7));
		
		assertEquals(2, board.get(2,0)); //
		assertEquals(2, board.get(2,1)); //
		assertEquals(2, board.get(2,2)); //
		assertEquals(2, board.get(2,3)); //
		assertEquals(2, board.get(2,4)); //
		assertEquals(2, board.get(2,5)); //
		assertEquals(1, board.get(2,6)); //
		assertEquals(2, board.get(2,7)); //
		
		assertEquals(0, board.get(3,0)); //
		assertEquals(0, board.get(3,1)); //
		assertEquals(0, board.get(3,2)); //
		assertEquals(0, board.get(3,3)); //
		assertEquals(0, board.get(3,4)); //
		assertEquals(0, board.get(3,5)); //
		assertEquals(0, board.get(3,6)); //
		assertEquals(0, board.get(3,7)); //
		
		assertEquals(0, board.get(4,0)); //
		assertEquals(0, board.get(4,1)); //
		assertEquals(0, board.get(4,2)); //
		assertEquals(0, board.get(4,3)); //
		assertEquals(0, board.get(4,4)); //
		assertEquals(0, board.get(4,5)); //
		assertEquals(0, board.get(4,6)); //
		assertEquals(0, board.get(4,7)); //
		
		assertEquals(0, board.get(5,0)); //
		assertEquals(0, board.get(5,1)); //
		assertEquals(0, board.get(5,2)); //
		assertEquals(0, board.get(5,3)); //
		assertEquals(0, board.get(5,4)); //
		assertEquals(0, board.get(5,5)); //
		assertEquals(0, board.get(5,6)); //
		assertEquals(0, board.get(5,7)); //
		
		
		assertEquals(4, board.get(6,0)); //
		assertEquals(4, board.get(6,1)); //
		assertEquals(4, board.get(6,2)); //
		assertEquals(4, board.get(6,3)); //
		assertEquals(4, board.get(6,4)); //
		assertEquals(4, board.get(6,5)); //
		assertEquals(4, board.get(6,6)); //
		assertEquals(4, board.get(6,7)); //
		
		
		assertEquals(6, board.get(7,0)); //
		assertEquals(8, board.get(7,1)); //
		assertEquals(10, board.get(7,2)); //
		assertEquals(12, board.get(7,3)); //
		assertEquals(14, board.get(7,4)); //
		assertEquals(10, board.get(7,5)); //
		assertEquals(8, board.get(7,6)); //
		assertEquals(6, board.get(7,7)); //
		
		createPiecesEverywhereBoard();
		board = new NibbleBoard(PiecesEveryWhereGameState);
		assertEquals(6, board.get(0, 0));
		assertEquals(1, board.get(0, 1));
		assertEquals(11, board.get(0, 2));
		assertEquals(13, board.get(0, 3));
		assertEquals(15, board.get(0, 4));
		assertEquals(2, board.get(0, 5));
		assertEquals(9, board.get(0, 6));
		assertEquals(7, board.get(0, 7));

		assertEquals(5, board.get(1, 0));
		assertEquals(2, board.get(1, 1));
		assertEquals(5, board.get(1, 2));
		assertEquals(4, board.get(1, 3));
		assertEquals(1, board.get(1, 4));
		assertEquals(5, board.get(1, 5));
		assertEquals(5, board.get(1, 6));
		assertEquals(5, board.get(1, 7));
		
		assertEquals(1, board.get(2,0)); //
		assertEquals(2, board.get(2,1)); //
		assertEquals(9, board.get(2,2)); //
		assertEquals(2, board.get(2,3)); //
		assertEquals(5, board.get(2,4)); //
		assertEquals(1, board.get(2,5)); //
		assertEquals(1, board.get(2,6)); //
		assertEquals(11, board.get(2,7)); //
		
		assertEquals(2, board.get(3,0)); //
		assertEquals(5, board.get(3,1)); //
		assertEquals(2, board.get(3,2)); //
		assertEquals(1, board.get(3,3)); //
		assertEquals(2, board.get(3,4)); //
		assertEquals(12, board.get(3,5)); //
		assertEquals(1, board.get(3,6)); //
		assertEquals(0, board.get(3,7)); //
		
		assertEquals(0, board.get(4,0)); //
		assertEquals(4, board.get(4,1)); //
		assertEquals(0, board.get(4,2)); //
		assertEquals(4, board.get(4,3)); //
		assertEquals(0, board.get(4,4)); //
		assertEquals(0, board.get(4,5)); //
		assertEquals(0, board.get(4,6)); //
		assertEquals(7, board.get(4,7)); //
		
		assertEquals(0, board.get(5,0)); //
		assertEquals(0, board.get(5,1)); //
		assertEquals(8, board.get(5,2)); //
		assertEquals(0, board.get(5,3)); //
		assertEquals(0, board.get(5,4)); //
		assertEquals(4, board.get(5,5)); //
		assertEquals(0, board.get(5,6)); //
		assertEquals(0, board.get(5,7)); //
		
		
		assertEquals(4, board.get(6,0)); //
		assertEquals(0, board.get(6,1)); //
		assertEquals(5, board.get(6,2)); //
		assertEquals(14, board.get(6,3)); //
		assertEquals(4, board.get(6,4)); //
		assertEquals(0, board.get(6,5)); //
		assertEquals(4, board.get(6,6)); //
		assertEquals(4, board.get(6,7)); //
		
		
		assertEquals(6, board.get(7,0)); //
		assertEquals(0, board.get(7,1)); //
		assertEquals(10, board.get(7,2)); //
		assertEquals(0, board.get(7,3)); //
		assertEquals(0, board.get(7,4)); //
		assertEquals(10, board.get(7,5)); //
		assertEquals(8, board.get(7,6)); //
		assertEquals(0, board.get(7,7)); //
	}

	public void testSet() {
		createStartingBoard();
		NibbleBoard board = new NibbleBoard(blankGameState);
		assertEquals(7, board.get(0, 0));
		board.set(0, 0, 12);
		assertEquals(12, board.get(0, 0));
		
		createPiecesEverywhereBoard();
		board = new NibbleBoard(PiecesEveryWhereGameState);
		assertEquals(6, board.get(0, 0));
		board.set(0, 0, 12);
		assertEquals(12, board.get(0, 0));
	}

}