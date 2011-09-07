package edu.neumont.learningChess.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.api.PieceDescription;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.api.TeamColor;

public class SerializedChessGameState {
	
	private static final int DARK_PAWN_ROW = 1;
	private static final int LIGHT_PAWN_ROW = 6;
	private static final Location DARK_KING_LOCATION = new Location(7,4);
	private static final Location LIGHT_KING_LOCATION = new Location(0,4);
	private static final int NUMBER_ROWS = 8;
	private static final int NUMBER_COLS = 8;
	private static final int BUFFER_SIZE = 32;
	

	public static ChessGameState deserialize(byte[] buffer) {
		ChessGameState chessGameState = new ChessGameState();
		NibbleBoard nibbleBoard= new NibbleBoard(buffer);
		List<Integer> metaValues = new ArrayList<Integer>();
		List<Location> rookLocations = new ArrayList<Location>();
		for (int row = 0; row < NUMBER_ROWS; row++) {
			for (int col = 0; col < NUMBER_COLS; col++) {
				int value = nibbleBoard.get(row, col);
				if(value == 1 || value == 2) 
					metaValues.add(value);
				else if(value != 0) {
					boolean hasMoved = true;
					PieceDescription piece = getPieceDescription(hasMoved, value);
					if(piece.getPieceType() == PieceType.ROOK)
						rookLocations.add(new Location(row,col));
					chessGameState.setPieceDescription(new Location(row, col), piece);
				}
				
			}
		}
		decodeMetaValues(chessGameState, rookLocations,metaValues);
		return chessGameState;
	}
	
	private static void decodeMetaValues(ChessGameState chessGameState, List<Location> rooks, List<Integer> metaValues) {
		
		// first 4 are rook hasMoveds
		for (int i = 0; i < 4; i++) {
			int metaValue = metaValues.remove(0);
			Location rookLocation = null;
			if(rooks.size() > 0) {
				rookLocation = rooks.remove(0);
				PieceDescription pieceDescription = chessGameState.getPieceDescription(rookLocation);
				PieceDescription newPieceDescription = new PieceDescription(pieceDescription.getColor(), getBooleanValue(metaValue), pieceDescription.getPieceType());
				chessGameState.setPieceDescription(rookLocation, newPieceDescription);
			}
		}
		
		// next two are have the kings moved (white and black)
		boolean hasWhiteKingMoved = getBooleanValue(metaValues.remove(0));
		if(!hasWhiteKingMoved) {
			PieceDescription whiteKingPieceDescription = new PieceDescription(TeamColor.LIGHT, false, PieceType.KING);
			chessGameState.setPieceDescription(LIGHT_KING_LOCATION, whiteKingPieceDescription);
		}
		
		boolean hasBlackKingMoved = getBooleanValue(metaValues.remove(0));
		if(!hasBlackKingMoved) {
			PieceDescription whiteKingPieceDescription = new PieceDescription(TeamColor.DARK, false, PieceType.KING);
			chessGameState.setPieceDescription(DARK_KING_LOCATION, whiteKingPieceDescription);
		}
		
		// next is the moving teams color
		boolean isWhiteMoving = getBooleanValue(metaValues.remove(0));
		chessGameState.setMovingTeamColor(isWhiteMoving ? TeamColor.LIGHT : TeamColor.DARK);
		
		// next one is a value indicating if the last move was a pawn who moved two
		boolean didPawnMoveTwo = getBooleanValue(metaValues.remove(0));
		// if the above is true, the row and column are encoded
		if(didPawnMoveTwo) {
			int row = getNext3BitValue(metaValues);
			int col = getNext3BitValue(metaValues);
			Location oldPawnMovedTwoLocation = new Location(row, col);
			PieceDescription oldPawnDescription = chessGameState.getPieceDescription(oldPawnMovedTwoLocation);
			int rowDiff = oldPawnDescription.getColor() == TeamColor.LIGHT ? -2 : 2;
			Location pawnMovedTwoLocation = new Location(row+rowDiff,col);
			chessGameState.setPawnMovedTwoLocation(pawnMovedTwoLocation);
		}
		
		updatePawns(chessGameState, LIGHT_PAWN_ROW, TeamColor.LIGHT);
		updatePawns(chessGameState, DARK_PAWN_ROW, TeamColor.DARK);
	}

	private static void updatePawns(ChessGameState chessGameState, int row, TeamColor color) {
		for (int i = 0; i < NUMBER_COLS; i++) {
			Location pawnLocation = new Location(row, i);
			PieceDescription pawnPieceDescription = chessGameState.getPieceDescription(pawnLocation);
			if(pawnPieceDescription != null && pawnPieceDescription.getPieceType() == PieceType.PAWN) {
				if(row == LIGHT_PAWN_ROW && pawnPieceDescription.getColor() == TeamColor.LIGHT) {
					PieceDescription newPawnPieceDescription = new PieceDescription(TeamColor.LIGHT, false, PieceType.PAWN);
					chessGameState.setPieceDescription(pawnLocation, newPawnPieceDescription);
				}
				else if(row == DARK_PAWN_ROW && pawnPieceDescription.getColor() == TeamColor.DARK) {
					PieceDescription newPawnPieceDescription = new PieceDescription(TeamColor.DARK, false, PieceType.PAWN);
					chessGameState.setPieceDescription(pawnLocation, newPawnPieceDescription);
				}
			}
		}
	}

	private static int getNext3BitValue(List<Integer> metaValues) {
		int value = 0;
		for (int i = 0; i < 3; i++) {
			if(getBooleanValue(metaValues.remove(0))) {
				value |= 1;
			}
			value = value << 1;
		}
		// or, shift, or, shift, or, shift, shift back (We only want to shift twice);
		value = value >> 1;
		if(value < 0 || value > 7)
			throw new IllegalArgumentException("Value was: " + value);
		return value;
	}

	private static boolean getBooleanValue(int value)
	{
		boolean isTrue = true;
		if(value == 1)
		{
			isTrue = true;
		}
		else if(value == 2)
		{
			isTrue = false;
		}
		return isTrue;
	}

	public static byte[] serialize(ChessGameState chessGameState) {
		byte[] buffer = new byte[BUFFER_SIZE];
		NibbleBoard board = new NibbleBoard(buffer);
		List<PieceDescription> rooks = new ArrayList<PieceDescription>();
		boolean hasWhiteKingMoved = false;
		boolean hasBlackKingMoved = false;
		for (int row = 0; row < NUMBER_ROWS; row++) {
			for (int col = 0; col < NUMBER_COLS; col++) {
				PieceDescription pieceDescription = chessGameState.getPieceDescription(new Location(row,col));
				if(pieceDescription != null){
					if(pieceDescription.getPieceType() == PieceType.ROOK) {
						rooks.add(pieceDescription);
					} else if(pieceDescription.getPieceType() == PieceType.KING) {
						boolean hasMoved = pieceDescription.hasMoved();
						if(pieceDescription.getColor() == TeamColor.LIGHT) {
							hasWhiteKingMoved = hasMoved;
						} else {
							hasBlackKingMoved = hasMoved;
						}
					}
					int value = getPieceValue(pieceDescription);
					board.set(row, col, value);
				}
				
			}
		}
		for (int i = 0; i < 4; i++) {
			PieceDescription rook = null;
			if(rooks.size() != 0)
				rook = rooks.remove(0);
			setEmptyValue(board, rook != null && rook.hasMoved());
		}
		
		setEmptyValue(board,hasWhiteKingMoved);
		setEmptyValue(board,hasBlackKingMoved);

		setEmptyValue(board,chessGameState.getMovingTeamColor() == TeamColor.LIGHT);
		Location lastMoveDescription = 
				chessGameState.getPawnMovedTwoLocation();
		if(lastMoveDescription != null) {
			setEmptyValue(board,true);
			int row = lastMoveDescription.getRow();
			set3BitValue(row, board);
			
			int col = lastMoveDescription.getColumn();
			set3BitValue(col, board);
		} else {
			setEmptyValue(board,false);
		}
		return buffer;
	}
	
	private static void set3BitValue(int value, NibbleBoard board)
	{
		for (int i = 0; i < 3; i++) {
			setEmptyValue(board,(value & 1) == 1);
			value >>= 1;
		}
	}

	private static void setEmptyValue(NibbleBoard board, boolean isSet) {
		Location location = getFirstUnusedLoaction(board);
		int value = isSet? 1: 2;
		board.set(location.getRow(), location.getColumn(), value);
	}

	private static Location getFirstUnusedLoaction(NibbleBoard board) {
		Location location = null;
		for (int row = 0; (location == null) && (row < 8); row++) {
			for (int col = 0; (location == null) && (col < 8); col++) {
				int value = board.get(row, col);
				if (value == 0) {
					location = new Location(row, col);
				}
			}
		}
		return location;
	}
	
	private static Map<Integer, PieceType> decodedPieceValues = new HashMap<Integer, PieceType>();
	private static Map<PieceType, Integer> encodedPieceValues = new HashMap<PieceType, Integer>();
	static {
		decodedPieceValues.put(4, PieceType.KING);
		decodedPieceValues.put(6, PieceType.QUEEN);
		decodedPieceValues.put(8, PieceType.BISHOP);
		decodedPieceValues.put(10, PieceType.KNIGHT);
		decodedPieceValues.put(12, PieceType.ROOK);
		decodedPieceValues.put(14, PieceType.PAWN);
		for (Entry<Integer, PieceType> e : decodedPieceValues.entrySet()) {
			encodedPieceValues.put(e.getValue(), e.getKey());
		}
	}
	
	private static int getPieceValue(PieceDescription piece) {
		PieceType pieceType = piece.getPieceType();
		return encodedPieceValues.get(pieceType) + (piece.getColor() == TeamColor.DARK ? 1 : 0);
	}
	
	private static PieceDescription getPieceDescription(boolean hasMoved, int value) {
		TeamColor teamColor = value % 2 == 0 ? TeamColor.LIGHT : TeamColor.DARK;
		// clearing the color bit
		value &= ~1;
		PieceType pieceType = decodedPieceValues.get(value);
		return new PieceDescription(teamColor, hasMoved, pieceType);
	}
}
