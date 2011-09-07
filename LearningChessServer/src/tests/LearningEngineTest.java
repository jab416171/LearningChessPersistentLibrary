package tests;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.engine.LearningEngine;
import edu.neumont.learningChess.model.Move;
import junit.framework.TestCase;

public class LearningEngineTest extends TestCase {

	private LearningEngine le;
	private static final String FILENAME = "TestFile";

	public void testCreate() {
		// creates a file
		boolean exceptionThrown = false;
		try {
			LearningEngine.create(FILENAME, 30);
		} catch (Throwable e) {
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);

	}

	public void testOpen(String fileName) {

		boolean exceptionThrown = false;
		try {
			le = LearningEngine.open(FILENAME);

		} catch (Throwable e) {
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);

	}

	public void testClose(LearningEngine learn) {

		boolean exceptionThrown = false;
		try {
			le.close();

		} catch (Throwable e) {
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);

	}

	public void testDelete(String fileName) {
		// deletes a file
		boolean exceptionThrown = false;
		try {
			LearningEngine.delete(FILENAME);
		} catch (Throwable e) {
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);
	}

	
	public void testGetMove() {
		
		fail("Not yet implemented");
	}
	public void testAnalyzeGameHistory() {
		fail("Not yet implemented");
	}

}
