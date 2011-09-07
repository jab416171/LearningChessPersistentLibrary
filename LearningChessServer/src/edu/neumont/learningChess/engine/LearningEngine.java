package edu.neumont.learningChess.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.engine.persistence.PersistentGameStateCache;
import edu.neumont.learningChess.model.ChessBoard;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.SingletonRandom;
import edu.neumont.learningChess.view.NullDisplay;
public class LearningEngine {

	private PersistentGameStateCache persistence;

	private LearningEngine(PersistentGameStateCache cache) {
		persistence = cache;
	}

	public static void create(String fileName, long recordSize, long maxListSize) {
		PersistentGameStateCache.create(fileName, recordSize, recordSize, maxListSize);
	}

	public static void delete(String fileName) {
		PersistentGameStateCache.delete(fileName);
	}

	public static LearningEngine open(String fileName) {
		LearningEngine ai = new LearningEngine(PersistentGameStateCache.open(fileName));
		return ai;
	}

	public void close() {
		persistence.close();
	}

	public Move getMove(ChessGameState gameState) {
		GameController control = new GameController(gameState);
		SearchResult result = findBestMove(control);
		return result.getMove();
	}

	public void analyzeGameHistory(MoveHistory history) {
		List<ExtendedMove> moves = history.getMoves();
		int count = 0;
		ExtendedMove currentMove = moves.get(count++);
		GameController controller = new GameController(new NullDisplay());
		ChessGameState current = controller.getCurrentGameState();
		Stack<ChessGameState> historyStack = new Stack<ChessGameState>();
		historyStack.add(current);
		while (!(controller.isCheckmate() || controller.isStalemate())) {
			
			controller.tryMove(currentMove);
			if (moves.size() != count)
				currentMove = moves.get(count++);
			historyStack.add(controller.getCurrentGameState());
		}
		if (controller.isCheckmate()) {
			analyzeStack(historyStack);
		} else if (controller.isStalemate()) {
			analyzeStaleStack(historyStack);
		} else
			throw new RuntimeException("Run out of moves without being in stalemate or checkmate.");

	}

	private void analyzeStack(Stack<ChessGameState> historyStack) {
		int denominator = (int) Math.floor(historyStack.size() / 2.0);
		boolean winner = false;
		int count = denominator;
		while (historyStack.size() > 1) {
			ChessGameState current = historyStack.pop();
			float value = count / denominator * (winner ? 1 : -1);
			GameStateInfo toUpdate = persistence.get(current);
			if (toUpdate == null)
				toUpdate = new GameStateInfo(SerializedChessGameState.serialize(current));
			toUpdate.addObservation(value);
			persistence.put(current, toUpdate);
			if (winner)
				count--;
			winner = !winner;
		}
	}

	private void analyzeStaleStack(Stack<ChessGameState> historyStack) {
		while (historyStack.size() > 1) {
			ChessGameState current = historyStack.pop();
			GameStateInfo toUpdate = persistence.get(current);
			toUpdate.addObservation(0);
			persistence.put(current, toUpdate);
		}
	}

	private SearchResult findBestMove(GameController gc) {
		ArrayList<SearchResult> results = null;
		float bestValue = 0;
		ChessBoard board = gc.getBoard();
		for (Iterator<Move> i = gc.getCurrentTeam().getMoves(board); i.hasNext();) {
			Move move = i.next();
			board.tryMove(move);
			float moveValue = getBoardValue(gc);
			board.undoTriedMove();
			if ((results == null) || (moveValue > bestValue)) {
				SearchResult result = new SearchResult(move, moveValue);

				results = new ArrayList<SearchResult>();

				results.add(result);
				bestValue = moveValue;
			} else if (moveValue == bestValue) {
				SearchResult result = new SearchResult(move, moveValue);
				results.add(result);
			}
		}
		return ((results == null) || (results.size() == 0)) ? null : results.get(SingletonRandom.nextInt(results.size()));
	}

	private float getBoardValue(GameController gc) {
		ChessGameState gameState = gc.getCurrentGameState();
		GameStateInfo info = persistence.get(gameState);
		return info != null ? info.getAverage() : 0;
	}

	private class SearchResult {
		private Move move;
		public SearchResult(Move move, float value) {
			this.move = move;
		}
		public Move getMove() {
			return move;
		}
	}

}