package edu.neumont.learningChess.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.controller.HistoryAnalyzer;
import edu.neumont.learningChess.engine.persistence.PersistentGameStateCache;
import edu.neumont.learningChess.model.ChessBoard;
import edu.neumont.learningChess.model.ChessPiece;
import edu.neumont.learningChess.model.ICheckChecker;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.MoveDescription;
import edu.neumont.learningChess.model.SingletonRandom;
import edu.neumont.learningChess.model.Team;
public class LearningEngine implements HistoryAnalyzer {

	private PersistentGameStateCache persistence;

	private LearningEngine(PersistentGameStateCache cache) {
		persistence = cache;
	}

	public static void create(String fileName, long maxListSize) {
		final long recordSize = SerializedChessGameState.getRecordSize();
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
		GameController control = new GameController(gameState, this);
		SearchResult result = findBestMove(control);
		return result.getMove();
	}
	
	public GameStateInfo getGameStateInfo(ChessGameState gameState) {
		return persistence.get(gameState);
	}

	public int analyzeGameHistory(MoveHistory history) {
		int size = history.getMoves().size();
		GameController controller = new GameController(this, history);
		controller.play();
		return size;
	}
	
	@Override
	public void analyzeStack(Stack<ChessGameState> historyStack) {
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
	
	@Override
	public void analyzeStaleStack(Stack<ChessGameState> historyStack) {
		while (historyStack.size() > 1) {
			ChessGameState current = historyStack.pop();
			GameStateInfo toUpdate = persistence.get(current);
			toUpdate.addObservation(0);
			persistence.put(current, toUpdate);
		}
	}

	private SearchResult findBestMove(GameController gameController) {
		ArrayList<SearchResult> results = null;
		float bestValue = 0;
		ChessBoard board = gameController.getBoard();
		for (Iterator<Move> i = gameController.getCurrentTeam().getMoves(board); i.hasNext();) {
			ExtendedMove move = new ExtendedMove(i.next());
			if(isLegalMove(move, board, gameController, gameController.getCurrentTeam())) {
				MoveDescription triedMove = board.tryMove(move);
				float moveValue = getBoardValue(gameController);
				board.undoTriedMove();
				if ((results == null) || (moveValue > bestValue)) {
					move.setPromotionPieceType(GameController.getPieceTypeFromChessPiece(triedMove.getPromotionPiece()));
					SearchResult result = new SearchResult(move, moveValue);
	
					results = new ArrayList<SearchResult>();
	
					results.add(result);
					bestValue = moveValue;
				} else if (moveValue == bestValue) {
					SearchResult result = new SearchResult(move, moveValue);
					results.add(result);
				}
			}
		}
		if(((results == null) || (results.size() == 0))) 
			return null;
		else {
			SearchResult searchResult = results.get(SingletonRandom.nextInt(results.size()));
//			if(gameController.getPiece(searchResult.move.getFrom()) instanceof Pawn) {
//				
//			}
			
			return searchResult;
		}
	}
	
	public boolean isLegalMove(Move move, ChessBoard board,ICheckChecker checkChecker, Team team) {
		ChessPiece movingPiece = board.getPiece(move.getFrom());
		Team movingTeam = movingPiece.getTeam();
		return (movingTeam == team) && movingPiece.isLegalMove(board, move) && !causesCheckmate(move, checkChecker, board, team);
	}
	
	private boolean causesCheckmate(Move move, ICheckChecker checkChecker, ChessBoard board, Team team) {
		board.tryMove(move);
		boolean result = checkChecker.isInCheck(team);
		board.undoTriedMove();
		
		return result;
	}

	

	private float getBoardValue(GameController gc) {
		ChessGameState gameState = gc.getCurrentGameState();
		GameStateInfo info = persistence.get(gameState);
		return info != null ? info.getAverage() : 0;
	}

	private class SearchResult {
		private ExtendedMove move;
		public SearchResult(ExtendedMove move, float value) {
			this.move = move;
		}
		public ExtendedMove getMove() {
			return move;
		}
	}

}