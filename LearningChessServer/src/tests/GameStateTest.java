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
import junit.framework.TestCase;

public class GameStateTest extends TestCase {

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

	public void testGameState() {
		GameState gs = new GameState();	
		
		for (int row = 0; row <= 7; row++) {
			for (int col = 0; col <= 7; col++) {
				ChessPiece piece = gs.getPiece(new Location(row,col));
				int value = gs.getPieceValue(piece);
				assertEquals(0, value);
			}
		}
	}

	public void testGameStateByteArray() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		createStartingBoard();
		GameState gState = new GameState(blankGameState, white, black);
		ChessPiece piece = gState.getPiece(new Location(0,0)); //
		assertEquals(7, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,1)); //
		assertEquals(9, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,2)); //
		assertEquals(11, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,3)); //
		assertEquals(13, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,4)); //
		assertEquals(15, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,5)); //
		assertEquals(11, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,6)); //
		assertEquals(9, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,7)); //
		assertEquals(7, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(1,0)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,1)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,2)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,3)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,4)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,5)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,6)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,7)); //
		assertEquals(5, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(2,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(3,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(4,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(5,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		
		piece = gState.getPiece(new Location(6,0)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,1)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,2)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,3)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,4)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,5)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,6)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,7)); //
		assertEquals(4, gState.getPieceValue(piece));
		
		
		piece = gState.getPiece(new Location(7,0)); //
		assertEquals(6, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,1)); //
		assertEquals(8, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,2)); //
		assertEquals(10, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,3)); //
		assertEquals(12, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,4)); //
		assertEquals(14, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,5)); //
		assertEquals(10, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,6)); //
		assertEquals(8, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,7)); //
		assertEquals(6, gState.getPieceValue(piece));
	}

	public void testSerialize() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		ChessPiece piece = gState.getPiece(new Location(0,0));
		assertEquals(7, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,1)); //
		assertEquals(9, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,2)); //
		assertEquals(11, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,3)); //
		assertEquals(13, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,4)); //
		assertEquals(15, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,5)); //
		assertEquals(11, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,6)); //
		assertEquals(9, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(0,7)); //
		assertEquals(7, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(1,0)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,1)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,2)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,3)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,4)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,5)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,6)); //
		assertEquals(5, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(1,7)); //
		assertEquals(5, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(2,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(2,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(3,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(3,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(4,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(4,7)); //
		assertEquals(0, gState.getPieceValue(piece));
		
		piece = gState.getPiece(new Location(5,0)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,1)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,2)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,3)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,4)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,5)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,6)); //
		assertEquals(0, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(5,7)); //
		assertEquals(0, gState.getPieceValue(piece));
			
		piece = gState.getPiece(new Location(6,0)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,1)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,2)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,3)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,4)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,5)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,6)); //
		assertEquals(4, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(6,7)); //
		assertEquals(4, gState.getPieceValue(piece));
			
		piece = gState.getPiece(new Location(7,0)); //
		assertEquals(6, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,1)); //
		assertEquals(8, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,2)); //
		assertEquals(10, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,3)); //
		assertEquals(12, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,4)); //
		assertEquals(14, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,5)); //
		assertEquals(10, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,6)); //
		assertEquals(8, gState.getPieceValue(piece));
		piece = gState.getPiece(new Location(7,7)); //
		assertEquals(6, gState.getPieceValue(piece));
		
		assertEquals(false, gState.getRookMoved(0));
		assertEquals(false, gState.getRookMoved(1));
		assertEquals(false, gState.getRookMoved(2));
		assertEquals(false, gState.getRookMoved(3));
		
		assertEquals(false, gState.getKingMoved(true));
		assertEquals(false, gState.getKingMoved(false));
		assertEquals(true, gState.isWhiteTeamsMove());
		assertEquals(false, gState.getPawnMovedTwo());
	}

	public void testGetPiece() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		String pieceName = "";
		boolean pieceIsWhite = false;
		ChessPiece piece = gState.getPiece(new Location(0,0));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Rook", pieceName);
		assertEquals(false, pieceIsWhite);
		
		piece = gState.getPiece(new Location(7,2));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Bishop", pieceName);
		assertEquals(true, pieceIsWhite);
		
		boolean exceptionOccured = false;
		try{
			piece = gState.getPiece(new Location(8,2)); // outside of the array
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		pieceName = "";
		pieceIsWhite = false;
		piece = gState.getPiece(new Location(0,0));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Rook", pieceName);
		assertEquals(true, pieceIsWhite);
		
		piece = gState.getPiece(new Location(3,1));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Pawn", pieceName);
		assertEquals(false, pieceIsWhite);
		
		piece = gState.getPiece(new Location(7,4));
		assertNull(piece);
	}

	public void testSetPiece() {
		Team white = new Team(Team.Color.LIGHT);
		Team black = new Team(Team.Color.DARK);
		createStartingBoard();
		GameState gState = new GameState(blankGameState, white, black);
		
		ChessPiece piece = gState.getPiece(new Location(0,0));
		assertEquals("Rook", piece.getName());
		piece = new Knight();
		piece.setLocation(new Location(0,0));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		piece = gState.getPiece(new Location(0,0));
		assertEquals("Knight", piece.getName());
				
		piece = gState.getPiece(new Location(2,7));
		assertNull(piece);
		piece = new Bishop();
		piece.setLocation(new Location(2,7));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		piece = gState.getPiece(new Location(2,7));
		assertEquals("Bishop", piece.getName());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, white, black);
		piece = gState.getPiece(new Location(2,1));
		assertNull(piece);
		piece = new Bishop();
		piece.setLocation(new Location(2,1));
		piece.setTeam(black);
		gState.setPiece(piece.getLocation(), piece);
		piece = gState.getPiece(new Location(2,1));
		assertEquals("Bishop", piece.getName());
		
	}

	public void testGetRookMoved() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.getRookMoved(0));
		assertFalse(gState.getRookMoved(1));
		assertFalse(gState.getRookMoved(2));
		assertFalse(gState.getRookMoved(3));
		boolean exceptionOccured = false;
		try{
			gState.getRookMoved(4);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertTrue(gState.getRookMoved(0));
		assertFalse(gState.getRookMoved(1));
		assertFalse(gState.getRookMoved(2));
		assertTrue(gState.getRookMoved(3));
		exceptionOccured = false;
		try{
			gState.getRookMoved(4);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testSetRookMoved() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setRookMoved(0, true);
		assertTrue(gState.getRookMoved(0));
		gState.setRookMoved(3, true);
		gState.setRookMoved(0, false);
		assertFalse(gState.getRookMoved(0));
		assertFalse(gState.getRookMoved(1));
		assertFalse(gState.getRookMoved(2));
		assertTrue(gState.getRookMoved(3));
		boolean exceptionOccured = false;
		try{
			gState.setRookMoved(4, true);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setRookMoved(0, true);
		assertTrue(gState.getRookMoved(0));
		gState.setRookMoved(2, true);
		gState.setRookMoved(0, false);
		assertFalse(gState.getRookMoved(0));
		assertFalse(gState.getRookMoved(1));
		assertTrue(gState.getRookMoved(2));
		assertTrue(gState.getRookMoved(3));
		exceptionOccured = false;
		try{
			gState.setRookMoved(4, true);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testGetKingMoved() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.getKingMoved(true));
		assertFalse(gState.getKingMoved(false));
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertEquals(true, gState.getKingMoved(true));
		assertFalse(gState.getKingMoved(false));
		
	}

	public void testSetKingMoved() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.getKingMoved(true));
		assertFalse(gState.getKingMoved(false));
		gState.setKingMoved(true, true);
		assertTrue(gState.getKingMoved(true));
		assertFalse(gState.getKingMoved(false));
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertTrue(gState.getKingMoved(true));
		assertFalse(gState.getKingMoved(false));
		gState.setKingMoved(false, true);
		assertTrue(gState.getKingMoved(true));
		assertTrue(gState.getKingMoved(false));
	}

	public void testIsWhiteTeamsMove() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertTrue(gState.isWhiteTeamsMove());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.isWhiteTeamsMove());
	}

	public void testSetTeamsMove() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setTeamsMove(false);
		assertFalse(gState.isWhiteTeamsMove());	
		gState.setTeamsMove(true);
		assertTrue(gState.isWhiteTeamsMove());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setTeamsMove(false);
		assertFalse(gState.isWhiteTeamsMove());	
		gState.setTeamsMove(true);
		assertTrue(gState.isWhiteTeamsMove());
	}

	public void testGetPawnMovedTwo() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.getPawnMovedTwo());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertTrue(gState.getPawnMovedTwo());
	}

	public void testSetPawnMovedTwo() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertFalse(gState.getPawnMovedTwo());
		gState.setPawnMovedTwo(true);
		assertTrue(gState.getPawnMovedTwo());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertTrue(gState.getPawnMovedTwo());
		gState.setPawnMovedTwo(false);
		assertFalse(gState.getPawnMovedTwo());
	}

	public void testGetPawnMovedTwoRow() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertEquals(0, gState.getPawnMovedTwoRow());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertEquals(4, gState.getPawnMovedTwoRow());
	}

	public void testSetPawnMovedTwoRow() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setPawnMovedTwoRow(1);
		assertEquals(1, gState.getPawnMovedTwoRow());
		boolean exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setPawnMovedTwoRow(1);
		assertEquals(1, gState.getPawnMovedTwoRow());
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testGetPawnMovedTwoCol() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertEquals(0, gState.getPawnMovedTwoCol());
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		assertEquals(5, gState.getPawnMovedTwoCol());
	}

	public void testSetPawnMovedTwoCol() {
		createStartingBoard();
		GameState gState = new GameState(blankGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setPawnMovedTwoCol(2);
		assertEquals(2, gState.getPawnMovedTwoCol());
		boolean exceptionOccured = false;
		try{
			gState.setPawnMovedTwoCol(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createPiecesEverywhereBoard();
		gState = new GameState(PiecesEveryWhereGameState, new Team(Team.Color.LIGHT), new Team(Team.Color.DARK));
		gState.setPawnMovedTwoCol(2);
		assertEquals(2, gState.getPawnMovedTwoCol());
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoCol(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			gState.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

}
