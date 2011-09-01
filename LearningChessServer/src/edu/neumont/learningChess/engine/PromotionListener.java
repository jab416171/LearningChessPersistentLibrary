package edu.neumont.learningChess.engine;

import edu.neumont.learningChess.api.IPromotionListener;
import edu.neumont.learningChess.api.PieceType;

public class PromotionListener implements IPromotionListener {

	private final PieceType promotionPieceType;

	public PromotionListener(PieceType promotionPieceType) {
		this.promotionPieceType = promotionPieceType;
	}

	@Override
	public PieceType getPromotionPieceType() {
		return promotionPieceType;
	}

}
