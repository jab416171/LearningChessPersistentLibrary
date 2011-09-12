package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sun.xml.internal.txw2.Document;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.engine.LearningEngine;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.Move;
import junit.framework.TestCase;

public class LearningEngineTest extends TestCase {

	private LearningEngine le;
	private static final String FILENAME = "TestFile";
	
	@Test
	public void testCreate() {
		// creates a file
		boolean exceptionThrown = false;
		try {
			LearningEngine.create(FILENAME, 30);
			LearningEngine.delete(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);

	}
	
	@Test
	public void testOpen() {

		boolean exceptionThrown = false;
		try {
			LearningEngine.create(FILENAME, 30);
			le = LearningEngine.open(FILENAME);
			le.close();
			LearningEngine.delete(FILENAME);
		} catch (Throwable e) {
			e.printStackTrace();

			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);

	}
	
	@Test
	public void testClose() {

		boolean exceptionThrown = false;
		try {
			LearningEngine.create(FILENAME, 30);
			le = LearningEngine.open(FILENAME);

			le.close();
			LearningEngine.delete(FILENAME);


		} catch (Throwable e) {
			e.printStackTrace();

			exceptionThrown = true;
		}
		
		
		assertFalse(exceptionThrown);

	}
	
	@Test
	public void testDelete() {
		// deletes a file
		boolean exceptionThrown = false;
		try {
			LearningEngine.create("other", 30);
			LearningEngine.delete("other");
		} catch (Throwable e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		assertFalse(exceptionThrown);
	}

	@Test
	public void testGetMove() {
		
		
		// take a gamestate
		File f = new File("gamestate.txt");
		String line = "";
		String result = "";
		try {
		BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)   {
				result+=line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ChessGameState cgs = Jsonizer.dejsonize(result, ChessGameState.class);
		
		//set up a learning engine
		LearningEngine.create(FILENAME, 100);
		LearningEngine eng = LearningEngine.open(FILENAME);
		Move m = eng.getMove(cgs);
		System.out.println("Move to: ");
		System.out.println("to="+m.getTo().toString());
		System.out.println("from="+m.getFrom().toString());
		eng.close();
		LearningEngine.delete(FILENAME);

		
	}
	
	@Test
	public void testAnalyzeGameHistory() {
		fail("Not yet implemented");
	}

}
