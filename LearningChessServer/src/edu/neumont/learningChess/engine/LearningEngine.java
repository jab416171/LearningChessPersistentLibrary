package edu.neumont.learningChess.engine;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import edu.neumont.learningChess.api.ChessGame;
import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.Move;
import edu.neumont.learningChess.engine.persistence.PersistentGameStateCache;
import edu.neumont.learningChess.model.GameStateHistory;
public class LearningEngine {

	private PersistentGameStateCache persistence;
	private Random random = new Random();

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

	public Move getMove(ChessGame ChessGame) {
		SearchResult result = findBestMove(ChessGame);
		return result == null ? null : result.getMove();
	}

	public void analyzeGameHistory(GameStateHistory history) {
		ChessGame current = new ChessGame();
		Enumeration<ChessGameState> gameStateHistories = history.getAllHistories();
		Stack<ChessGameState> historyStack = new Stack<ChessGameState>();
		while (gameStateHistories.hasMoreElements()) {
			ChessGameState currentMove = gameStateHistories.nextElement();
			current.setGameState(currentMove);
			if (!(current.isCheckMate() || current.isStaleMate())) {
				historyStack.add(current.getGameState());
			}
		}
		if (current.isCheckMate()) {
			analyzeStack(historyStack);
		} else if (current.isStaleMate()) {
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
			float value = count / (denominator * (winner ? 1 : -1));
			GameStateInfo toUpdate = persistence.get(current);
			if (toUpdate == null)
				toUpdate = new GameStateInfo(0, 0);
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

	private SearchResult findBestMove(ChessGame game) {
		List<SearchResult> results = null;
		float bestValue = 0;

		for (Enumeration<Move> possibleMoves = game.getPossibleMoves(); possibleMoves.hasMoreElements();) {
			Move move = possibleMoves.nextElement();
			game.makeMove(game.getMoveDescription(move, null));
			float moveValue = getBoardValue(game.getGameState());
			game.unMakeMove();
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
		if ((results == null) || (results.size() == 0))
			return null;
		else
			return results.get(random.nextInt(results.size()));
	}

	private float getBoardValue(ChessGameState chessGameState) {
		GameStateInfo info = persistence.get(chessGameState);
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