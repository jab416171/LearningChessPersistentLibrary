package tests;

import ai.MetaBoard;
import junit.framework.TestCase;

public class MetaBoardTest extends TestCase {

	MetaBoard blankGameState = null;
	MetaBoard wPawnMovedGameState = null;
	MetaBoard blackHasMovedPiecesGameState = null;
	
	public void createStartingBoard()
	{
		//starting board, no one has moved
		// white teams turn
		MetaBoard mBoard = new MetaBoard();
		mBoard.setRookMoved(0, false);
		mBoard.setRookMoved(1, false);
		mBoard.setRookMoved(2, false);
		mBoard.setRookMoved(3, false);
		mBoard.setKingMoved(true, false);
		mBoard.setKingMoved(false, false);
		mBoard.setTeamsMove(true);
		mBoard.setPawnMovedTwo(false);
		mBoard.setPawnMovedTwoRow(0);
		mBoard.setPawnMovedTwoCol(0);
		blankGameState = mBoard;
	}
	
	public void createWhitePawnMovedBoard()
	{
		//white pawn moved 2 to 4,3
		//black teams turn
		MetaBoard mBoard = new MetaBoard();
		mBoard.setRookMoved(0, false);
		mBoard.setRookMoved(1, false);
		mBoard.setRookMoved(2, false);
		mBoard.setRookMoved(3, false);
		mBoard.setKingMoved(true, false);
		mBoard.setKingMoved(false, false);
		mBoard.setTeamsMove(false);
		mBoard.setPawnMovedTwo(true);
		mBoard.setPawnMovedTwoRow(4);
		mBoard.setPawnMovedTwoCol(3);
		wPawnMovedGameState = mBoard;
	}
	
	public void createBlackHasMovedPiecesBoard()
	{
		//black pawn has moved 2  to 3,0
		//second black rook has moved
		//first white rook has moved
		//black king has moved
		//white teams turn
		MetaBoard mBoard = new MetaBoard();
		mBoard.setPawnMovedTwo(true);
		mBoard.setRookMoved(0, false);
		mBoard.setRookMoved(1, true);
		mBoard.setRookMoved(2, true);
		mBoard.setRookMoved(3, false);
		mBoard.setKingMoved(true, true); 
		mBoard.setKingMoved(false, true);
		mBoard.setTeamsMove(true);
		mBoard.setPawnMovedTwoRow(3);
		mBoard.setPawnMovedTwoCol(0);
		blackHasMovedPiecesGameState = mBoard;
	}
	

	public void testGetRookMoved() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertFalse(mBoard.getRookMoved(0));
		assertFalse(mBoard.getRookMoved(1));
		assertFalse(mBoard.getRookMoved(2));
		assertFalse(mBoard.getRookMoved(3));
		boolean exceptionOccured = false;
		try{
			mBoard.getRookMoved(4);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertFalse(mBoard.getRookMoved(0));
		assertFalse(mBoard.getRookMoved(1));
		assertFalse(mBoard.getRookMoved(2));
		assertFalse(mBoard.getRookMoved(3));
		exceptionOccured = false;
		try{
			mBoard.getRookMoved(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertFalse(mBoard.getRookMoved(0));
		assertTrue(mBoard.getRookMoved(1));
		assertTrue(mBoard.getRookMoved(2));
		assertFalse(mBoard.getRookMoved(3));
		exceptionOccured = false;
		try{
			mBoard.getRookMoved(Integer.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testSetRookMoved() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		mBoard.setRookMoved(0, true);
		assertTrue(mBoard.getRookMoved(0));
		assertFalse(mBoard.getRookMoved(1));
		mBoard.setRookMoved(3, true);
		mBoard.setRookMoved(0, false);
		assertFalse(mBoard.getRookMoved(0));
		assertTrue(mBoard.getRookMoved(3));
		boolean exceptionOccured = false;
		try{
			mBoard.setRookMoved(4, true);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		mBoard.setRookMoved(0, true);
		assertTrue(mBoard.getRookMoved(0));
		assertFalse(mBoard.getRookMoved(1));	
		mBoard.setRookMoved(3, true);
		mBoard.setRookMoved(0, false);
		assertFalse(mBoard.getRookMoved(0));
		assertTrue(mBoard.getRookMoved(3));
		exceptionOccured = false;
		try{
			mBoard.setRookMoved(Integer.MIN_VALUE, true);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		mBoard.setRookMoved(0, true);
		assertTrue(mBoard.getRookMoved(0));
		assertTrue(mBoard.getRookMoved(1));
		mBoard.setRookMoved(3, true);
		mBoard.setRookMoved(0, false);
		assertFalse(mBoard.getRookMoved(0));
		assertTrue(mBoard.getRookMoved(3));
	}

	public void testGetKingMoved() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertFalse(mBoard.getKingMoved(true));
		assertFalse(mBoard.getKingMoved(false));
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertFalse(mBoard.getKingMoved(true));
		assertFalse(mBoard.getKingMoved(false));
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertTrue(mBoard.getKingMoved(true));
		assertTrue(mBoard.getKingMoved(false));
	}

	public void testSetKingMoved() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertFalse(mBoard.getKingMoved(true));
		assertFalse(mBoard.getKingMoved(false));
		mBoard.setKingMoved(true, true);
		assertTrue(mBoard.getKingMoved(true));
		assertFalse(mBoard.getKingMoved(false));
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertFalse(mBoard.getKingMoved(true));
		assertFalse(mBoard.getKingMoved(false));
		mBoard.setKingMoved(false, true);
		assertTrue(mBoard.getKingMoved(false));
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertTrue(mBoard.getKingMoved(true));
		assertTrue(mBoard.getKingMoved(false));
		mBoard.setKingMoved(true, false);
		assertFalse(mBoard.getKingMoved(true));
		mBoard.setKingMoved(false, false);
		assertFalse(mBoard.getKingMoved(false));	
	}

	public void testIsWhiteTeamsMove() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertTrue(mBoard.isWhiteTeamsMove());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertEquals(false, mBoard.isWhiteTeamsMove());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertTrue(mBoard.isWhiteTeamsMove());
	}

	public void testSetTeamsMove() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		mBoard.setTeamsMove(false);
		assertFalse(mBoard.isWhiteTeamsMove());	
		mBoard.setTeamsMove(true);
		assertTrue(mBoard.isWhiteTeamsMove());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		mBoard.setTeamsMove(false);
		assertFalse(mBoard.isWhiteTeamsMove());	
		mBoard.setTeamsMove(true);
		assertTrue(mBoard.isWhiteTeamsMove());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		mBoard.setTeamsMove(false);
		assertFalse(mBoard.isWhiteTeamsMove());	
		mBoard.setTeamsMove(true);
		assertTrue(mBoard.isWhiteTeamsMove());
	}

	public void testGetPawnMovedTwo() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertFalse(mBoard.getPawnMovedTwo());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertEquals(true, mBoard.getPawnMovedTwo());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertTrue(mBoard.getPawnMovedTwo());
	}

	public void testSetPawnMovedTwo() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertFalse(mBoard.getPawnMovedTwo());
		mBoard.setPawnMovedTwo(true);
		assertTrue(mBoard.getPawnMovedTwo());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertEquals(true, mBoard.getPawnMovedTwo());
		mBoard.setPawnMovedTwo(false);
		assertFalse(mBoard.getPawnMovedTwo());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertTrue(mBoard.getPawnMovedTwo());
		mBoard.setPawnMovedTwo(false);
		assertFalse(mBoard.getPawnMovedTwo());
	}

	public void testGetPawnMovedTwoRow() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertEquals(0, mBoard.getPawnMovedTwoRow());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertEquals(4, mBoard.getPawnMovedTwoRow());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertEquals(3, mBoard.getPawnMovedTwoRow());
	}

	public void testSetPawnMovedTwoRow() {  
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		mBoard.setPawnMovedTwoRow(1);
		assertEquals(1, mBoard.getPawnMovedTwoRow());
		boolean exceptionOccured = false;
		try{
			mBoard.setPawnMovedTwoRow(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createWhitePawnMovedBoard();  
		mBoard = wPawnMovedGameState;
		mBoard.setPawnMovedTwoRow(5);
		assertEquals(5, mBoard.getPawnMovedTwoRow());
		exceptionOccured = false;
		try{
			mBoard.setPawnMovedTwoRow(Integer.MIN_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createBlackHasMovedPiecesBoard(); 
		mBoard = blackHasMovedPiecesGameState;
		mBoard.setPawnMovedTwoRow(4);
		assertEquals(4, mBoard.getPawnMovedTwoRow());
		exceptionOccured = false;
		try{
			mBoard.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}

	public void testGetPawnMovedTwoCol() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		assertEquals(0, mBoard.getPawnMovedTwoCol());
		
		createWhitePawnMovedBoard();
		mBoard = wPawnMovedGameState;
		assertEquals(3, mBoard.getPawnMovedTwoCol());
		
		createBlackHasMovedPiecesBoard();
		mBoard = blackHasMovedPiecesGameState;
		assertEquals(0, mBoard.getPawnMovedTwoCol());
	}

	public void testSetPawnMovedTwoCol() {
		createStartingBoard();
		MetaBoard mBoard = blankGameState;
		mBoard.setPawnMovedTwoCol(2);
		assertEquals(2, mBoard.getPawnMovedTwoCol());
		boolean exceptionOccured = false;
		try{
			mBoard.setPawnMovedTwoCol(8);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createWhitePawnMovedBoard();  
		mBoard = wPawnMovedGameState;
		mBoard.setPawnMovedTwoCol(4);
		assertEquals(4, mBoard.getPawnMovedTwoCol());
		exceptionOccured = false;
		try{
			mBoard.setPawnMovedTwoRow(Integer.MAX_VALUE);
		}
		catch(Throwable e){
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
		
		createBlackHasMovedPiecesBoard(); 
		mBoard = blackHasMovedPiecesGameState;
		mBoard.setPawnMovedTwoCol(1);
		assertEquals(1, mBoard.getPawnMovedTwoCol());
	}
}
