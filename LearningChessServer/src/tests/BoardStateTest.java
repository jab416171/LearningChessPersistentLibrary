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
import ai.BoardState;
import junit.framework.TestCase;

public class BoardStateTest extends TestCase {
	//7, 9, 11, 13, 15, 11, 9, 7
	//5, 5, 5, 5, 5, 5, 5, 5
	//0, 0, 0, 0, 0, 0, 0, 1
	//1, 0, 0, 0, 1, 1, 0, 0
	//0, 0, 0, 4, 0, 0, 0, 0
	//0, 0, 0, 0, 0, 0, 0, 0
	//4, 4, 4, 0, 4, 4, 4, 4
	//6, 8, 10, 12, 14, 10, 8, 6
	
	BoardState blankGameState = null;
	BoardState wPawnMovedGameState = null;
	BoardState PiecesEveryWhereGameState = null;
	
	public void createStartingBoard()
	{
		BoardState bState = new BoardState();	
		//black teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,1));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Queen();
		piece.setLocation(new Location(0,3));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(0,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,5));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(0,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		//black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(1,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,1));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece( piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,3));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,5));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		//empty squares
		bState.setEmptyPiece(new Location(2,0), null);
		bState.setEmptyPiece(new Location(2,1), null);
		bState.setEmptyPiece(new Location(2,2), null);
		bState.setEmptyPiece(new Location(2,3), null);
		bState.setEmptyPiece(new Location(2,4), null);
		bState.setEmptyPiece(new Location(2,5), null);
		bState.setEmptyPiece(new Location(2,6), null);
		bState.setEmptyPiece(new Location(2,7), null);
		
		bState.setEmptyPiece(new Location(3,0), null);
		bState.setEmptyPiece(new Location(3,1), null);
		bState.setEmptyPiece(new Location(3,2), null);
		bState.setEmptyPiece(new Location(3,3), null);
		bState.setEmptyPiece(new Location(3,4), null);
		bState.setEmptyPiece(new Location(3,5), null);
		bState.setEmptyPiece(new Location(3,6), null);
		bState.setEmptyPiece(new Location(3,7), null);
		
		bState.setEmptyPiece(new Location(4,0), null);
		bState.setEmptyPiece(new Location(4,1), null);
		bState.setEmptyPiece(new Location(4,2), null);
		bState.setEmptyPiece(new Location(4,3), null);
		bState.setEmptyPiece(new Location(4,4), null);
		bState.setEmptyPiece(new Location(4,5), null);
		bState.setEmptyPiece(new Location(4,6), null);
		bState.setEmptyPiece(new Location(4,7), null);
		
		bState.setEmptyPiece(new Location(5,0), null);
		bState.setEmptyPiece(new Location(5,1), null);
		bState.setEmptyPiece(new Location(5,2), null);
		bState.setEmptyPiece(new Location(5,3), null);
		bState.setEmptyPiece(new Location(5,4), null);
		bState.setEmptyPiece(new Location(5,5), null);
		bState.setEmptyPiece(new Location(5,6), null);
		bState.setEmptyPiece(new Location(5,7), null);
		
		//white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		//white teams back row
		piece = new Rook();
		piece.setLocation(new Location(7,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Queen();
		piece.setLocation(new Location(7,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(7,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(7,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		blankGameState = bState;
	}
	
	public void createWhitePawnMovedBoard()
	{
		BoardState bState = new BoardState();	
		//black teams back row
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,1));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Queen();
		piece.setLocation(new Location(0,3));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(0,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,5));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(0,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(0,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		//black teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(1,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,1));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,3));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,5));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		//empty squares
		bState.setEmptyPiece(new Location(2,0), null);
		bState.setEmptyPiece(new Location(2,1), null);
		bState.setEmptyPiece(new Location(2,2), null);
		bState.setEmptyPiece(new Location(2,3), null);
		bState.setEmptyPiece(new Location(2,4), null);
		bState.setEmptyPiece(new Location(2,5), null);
		bState.setEmptyPiece(new Location(2,6), null);
		bState.setEmptyPiece(new Location(2,7), null);
		
		bState.setEmptyPiece(new Location(3,0), null);
		bState.setEmptyPiece(new Location(3,1), null);
		bState.setEmptyPiece(new Location(3,2), null);
		bState.setEmptyPiece(new Location(3,3), null);
		bState.setEmptyPiece(new Location(3,4), null);
		bState.setEmptyPiece(new Location(3,5), null);
		bState.setEmptyPiece(new Location(3,6), null);
		bState.setEmptyPiece(new Location(3,7), null);
		
		bState.setEmptyPiece(new Location(4,0), null);
		bState.setEmptyPiece(new Location(4,1), null);
		bState.setEmptyPiece(new Location(4,2), null);
		piece = new Pawn();
		piece.setLocation(new Location(4,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(4,4), null);
		bState.setEmptyPiece(new Location(4,5), null);
		bState.setEmptyPiece(new Location(4,6), null);
		bState.setEmptyPiece(new Location(4,7), null);
		
		bState.setEmptyPiece(new Location(5,0), null);
		bState.setEmptyPiece(new Location(5,1), null);
		bState.setEmptyPiece(new Location(5,2), null);
		bState.setEmptyPiece(new Location(5,3), null);
		bState.setEmptyPiece(new Location(5,4), null);
		bState.setEmptyPiece(new Location(5,5), null);
		bState.setEmptyPiece(new Location(5,6), null);
		bState.setEmptyPiece(new Location(5,7), null);
		
		//white teams pawn row
		piece = new Pawn();
		piece.setLocation(new Location(6,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(6,3), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		//white teams back row
		piece = new Rook();
		piece.setLocation(new Location(7,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Queen();
		piece.setLocation(new Location(7,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(7,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(7,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		wPawnMovedGameState = bState;
	}
	
	public void createPiecesEverywhereBoard()
	{
		BoardState bState = new BoardState();	
		ChessPiece piece = new Rook();
		piece.setLocation(new Location(0,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(0,1), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(0,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Queen();
		piece.setLocation(new Location(0,3));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(0,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(0,5), null);
		
		piece = new Knight();
		piece.setLocation(new Location(0,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(0,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		

		piece = new Pawn();
		piece.setLocation(new Location(1,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(1,1), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(1,4), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,5));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,6));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(1,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(2,0), null);
		bState.setEmptyPiece(new Location(2,1), null);
		piece = new Knight();
		piece.setLocation(new Location(2,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(2,3), null);
		piece = new Pawn();
		piece.setLocation(new Location(2,4));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(2,5), null);
		bState.setEmptyPiece(new Location(2,6), null);
		piece = new Bishop();
		piece.setLocation(new Location(2,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece); 
		
		bState.setEmptyPiece(new Location(3,0), null);
		piece = new Pawn();
		piece.setLocation(new Location(3,1));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(3,2), null);
		bState.setEmptyPiece(new Location(3,3), null);
		bState.setEmptyPiece(new Location(3,4), null);
		piece = new Queen();
		piece.setLocation(new Location(3,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(3,6), null);
		bState.setEmptyPiece(new Location(3,7), null);
		
		bState.setEmptyPiece(new Location(4,0), null);
		piece = new Pawn();
		piece.setLocation(new Location(4,1));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(4,2), null);
		piece = new Pawn();
		piece.setLocation(new Location(4,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(4,4), null);
		bState.setEmptyPiece(new Location(4,5), null);
		bState.setEmptyPiece(new Location(4,6), null);
		piece = new Rook();
		piece.setLocation(new Location(4,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(5,0), null);
		bState.setEmptyPiece(new Location(5,1), null);
		piece = new Knight();
		piece.setLocation(new Location(5,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(5,3), null);
		bState.setEmptyPiece(new Location(5,4), null);
		piece = new Pawn();
		piece.setLocation(new Location(5,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		bState.setEmptyPiece(new Location(5,6), null);
		bState.setEmptyPiece(new Location(5,7), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(6,1), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,2));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		
		piece = new King();
		piece.setLocation(new Location(6,3));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,4));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(6,5), null);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Pawn();
		piece.setLocation(new Location(6,7));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Rook();
		piece.setLocation(new Location(7,0));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(7,1), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,2));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(7,3), null);
		
		bState.setEmptyPiece(new Location(7,4), null);
		
		piece = new Bishop();
		piece.setLocation(new Location(7,5));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		piece = new Knight();
		piece.setLocation(new Location(7,6));
		piece.setTeam(new Team(Team.Color.LIGHT));
		bState.setPiece(piece);
		
		bState.setEmptyPiece(new Location(7,7), null);
		PiecesEveryWhereGameState = bState;
	}
	
	public void testGetPiece() {
		createStartingBoard();
		BoardState bState = blankGameState;
		String pieceName = "";
		boolean pieceIsWhite = false;
		ChessPiece piece = bState.getPiece(new Location(0,0));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Rook", pieceName);
		assertEquals(false, pieceIsWhite);
		
		piece = bState.getPiece(new Location(7,2));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("Bishop", pieceName);
		assertEquals(true, pieceIsWhite);
		
		boolean exceptionOccured = false;
		try{
			piece = bState.getPiece(new Location(8,2)); // outside of the array
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		
		createWhitePawnMovedBoard();
		bState = wPawnMovedGameState;
		pieceName = "";
		pieceIsWhite = false;
		piece = bState.getPiece(new Location(0,4));
		pieceName = piece.getName();
		pieceIsWhite = piece.getTeam().isWhite();
		assertEquals("King", pieceName);
		assertEquals(false, pieceIsWhite);
		
		piece = bState.getPiece(new Location(6,3));	//empty square
		exceptionOccured = false;
		try{
			pieceName = piece.getName();
			pieceIsWhite = piece.getTeam().isWhite();
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
	}

	public void testIsWhitePiece() {
		createStartingBoard();
		BoardState bState = blankGameState;
		assertEquals(false, bState.isWhitePiece(new Location(0, 0)));
		boolean exceptionOccured = false;
		try{
			bState.isWhitePiece(new Location(3, 1));
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		assertEquals(true, bState.isWhitePiece(new Location(7, 0)));
		
		createWhitePawnMovedBoard();
		bState = wPawnMovedGameState;
		assertEquals(false, bState.isWhitePiece(new Location(0, 0)));
		assertEquals(true, bState.isWhitePiece(new Location(4, 3)));
		assertEquals(true, bState.isWhitePiece(new Location(7, 0)));
		
		exceptionOccured = false;
		try{
			bState.isWhitePiece(new Location(8, 0));
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testSetPiece() {
		createStartingBoard();
		BoardState bState = blankGameState;
		
		ChessPiece piece = bState.getPiece(new Location(0,0));
		assertEquals("Rook", piece.getName());
		piece = new Knight();
		piece.setLocation(new Location(0,0));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		piece = bState.getPiece(new Location(0,0));
		assertEquals("Knight", piece.getName());
		
		
		piece = bState.getPiece(new Location(2,7));
		assertNull(piece);
		piece = new Bishop();
		piece.setLocation(new Location(2,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		piece = bState.getPiece(new Location(2,7));
		assertEquals("Bishop", piece.getName());
		
		createWhitePawnMovedBoard();
		bState = wPawnMovedGameState;
		bState.setEmptyPiece(new Location(0,0), null);
		piece = bState.getPiece(new Location(0,0));
		assertNull(piece);
		
		boolean exceptionOccured = false;
		try{
			piece = bState.getPiece(new Location(9,7));
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

	}
	
	public void testSetEmptyPiece()
	{
		createStartingBoard();
		BoardState bState = blankGameState;
		
		ChessPiece piece = bState.getPiece(new Location(0,0));
		assertEquals("Rook", piece.getName());
		bState.setEmptyPiece(new Location(0,0), null);
		piece = bState.getPiece(new Location(0,0));
		assertNull(piece);
		
		
		piece = bState.getPiece(new Location(2,7));
		assertNull(piece);
		piece = new Bishop();
		piece.setLocation(new Location(2,7));
		piece.setTeam(new Team(Team.Color.DARK));
		bState.setPiece(piece);
		piece = bState.getPiece(new Location(2,7));
		assertEquals("Bishop", piece.getName());
		
		createWhitePawnMovedBoard();
		bState = wPawnMovedGameState;
		bState.setEmptyPiece(new Location(0,0), null);
		piece = bState.getPiece(new Location(0,0));
		assertNull(piece);	
		boolean exceptionOccured = false;
		try{
			piece = bState.getPiece(new Location(9,7));
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		exceptionOccured = false;
		try{
			bState.setEmptyPiece(new Location(9,8), null);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);

	}
}
