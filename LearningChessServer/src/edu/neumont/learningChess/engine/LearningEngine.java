package edu.neumont.learningChess.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.neumont.chessModel.Board.ChessBoard;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.learningChess.engine.persistence.PersistentGameStateCache;
public class LearningEngine{

	private PersistentGameStateCache persistence;

	private LearningEngine(PersistentGameStateCache cache) {
		persistence = cache;
	}
	
	public static void create(String fileName, long recordSize, long maxListSize){
		PersistentGameStateCache.create(fileName, recordSize, recordSize, maxListSize);
	}
	
	public static void delete(String fileName){
		PersistentGameStateCache.delete(fileName);
	}
	
	
	public static LearningEngine open(String fileName){
		LearningEngine ai = new LearningEngine(PersistentGameStateCache.open(fileName));
		return ai;
	}
	
	
	public void close(){
		persistence.close();
	}
	
	
	public Move getMove(ChessGame ChessGame){
		GameController control = new GameController(ChessGame);
		SearchResult result = findBestMove( control);
		return result.getMove();
	}
	
	public void analyzeGameHistory(ChessGameHistory history){
		List<Move> moves = history.getMoves();
		int count = 0;
		Move currentMove = moves.get(count ++);
		GameController controller = new GameController( new NullDisplay() );
		ChessGame current = controller.getChessGame();
		Stack<ChessGame> historyStack = new Stack<ChessGame>();
		historyStack.add(current);
		while(!(controller.isCheckmate() || controller.isStalemate())){
			controller.play(currentMove);
			if(moves.size() != count)
				currentMove = moves.get(count ++);
			historyStack.add(controller.getChessGame());
		}
		if(controller.isCheckmate()){
			analyzeStack(historyStack);                    
		}
		else if(controller.isStalemate()){
			analyzeStaleStack(historyStack);  
		}
		else
			throw new RuntimeException("Run out of moves without being in stalemate or checkmate.");
		
	}
	
	private void analyzeStack(Stack<ChessGame> historyStack){
		int denominator =  (int)Math.floor(historyStack.size()/2.0);
		boolean winner = false;
		int count = denominator;
		while(historyStack.size() > 1){
			ChessGame current = historyStack.pop();
			float value = count/denominator * (winner ? 1 : -1);
			ChessGameInfo toUpdate = persistence.get(current);
			if(toUpdate == null)
				toUpdate = new ChessGameInfo(current.serialize(), 0 ,0);
			toUpdate.addObservation(value);
			persistence.put(toUpdate);
			if(winner)
				count --;
			winner = !winner;
		}
	}
	
	private void analyzeStaleStack(Stack<ChessGame> historyStack){
		while(historyStack.size() > 1){
			ChessGame current = historyStack.pop();
			ChessGameInfo toUpdate = persistence.get(current);
			toUpdate.addObservation(0);
			persistence.put(toUpdate);
		}
	}
	
	
	
	private SearchResult findBestMove( GameController gc) {
		ArrayList<SearchResult> results = null;
		float bestValue = 0;
		ChessBoard board = gc.getBoard();
		for (Iterator<Move> i = gc.getCurrentTeam().getMoves(board); i.hasNext(); ) {
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
		if ((results == null) || (results.size() == 0))
			return null;
		else
			return results.get(SingletonRandom.nextInt(results.size()));
	}
	
	private float getBoardValue(GameController gc) {
		ChessGame ChessGame = gc.getChessGame();
		ChessGameInfo info = persistence.get(ChessGame);
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