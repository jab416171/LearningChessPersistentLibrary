package tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.engine.GameStateInfo;
import edu.neumont.learningChess.engine.SerializedChessGameState;
import edu.neumont.learningChess.engine.persistence.PersistentGameStateCache;
import edu.neumont.learningChess.json.Jsonizer;

public class PersistentGameStateCacheTest {

private static final String FILENAME = "PersistentTestCache";

//	@Test
//	public void testCreate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDelete() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testOpen() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClose() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testPutAndGet() {
		PersistentGameStateCache.create(FILENAME,8);
		PersistentGameStateCache cache = PersistentGameStateCache.open(FILENAME);
		String gameStateString = "{\"movingTeamColor\":\"LIGHT\",\"pieceDescriptions\":[[{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"ROOK\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"KNIGHT\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"BISHOP\"},null,{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"KING\"},null,{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"KNIGHT\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"ROOK\"}],[{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},null,{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":false,\"pieceType\":\"PAWN\"}],[null,null,null,null,null,null,null,null],[null,null,{\"teamColor\":\"LIGHT\",\"hasMoved\":true,\"pieceType\":\"BISHOP\"},null,{\"teamColor\":\"LIGHT\",\"hasMoved\":true,\"pieceType\":\"PAWN\"},null,null,null],[null,null,null,null,null,null,null,null],[{\"teamColor\":\"DARK\",\"hasMoved\":true,\"pieceType\":\"PAWN\"},{\"teamColor\":\"DARK\",\"hasMoved\":true,\"pieceType\":\"PAWN\"},null,null,null,null,null,{\"teamColor\":\"DARK\",\"hasMoved\":true,\"pieceType\":\"PAWN\"}],[null,null,{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},{\"teamColor\":\"LIGHT\",\"hasMoved\":true,\"pieceType\":\"QUEEN\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"PAWN\"},null],[{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"ROOK\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"KNIGHT\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"BISHOP\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"QUEEN\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"KING\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"BISHOP\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"KNIGHT\"},{\"teamColor\":\"DARK\",\"hasMoved\":false,\"pieceType\":\"ROOK\"}]]}";
		ChessGameState firstGameState = Jsonizer.dejsonize(gameStateString, ChessGameState.class);
		byte[] firstByte = SerializedChessGameState.serialize(firstGameState);
		GameStateInfo firstGameStateInfo = new GameStateInfo(1,1);
		cache.put(firstGameState, firstGameStateInfo);
		
		
		ChessGameState secondGameState = Jsonizer.dejsonize(gameStateString, ChessGameState.class);
		byte[] secondByte = SerializedChessGameState.serialize(secondGameState);
		GameStateInfo secondGameStateInfo = cache.get(secondGameState);
		
		assertTrue(firstGameStateInfo.equals(secondGameStateInfo));
		assertTrue(Arrays.equals(firstByte,secondByte));
		assertTrue(firstGameState.equals(secondGameState));
	}
}
