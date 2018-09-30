package com.perhac.games.clickthemarbles.game.viewmodel;

import com.perhac.games.clickthemarbles.model.Marble;
import com.perhac.games.clickthemarbles.model.MarbleType;
import com.perhac.games.clickthemarbles.model.Piece;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public class MarbleViewModel {

    private final Marble marble;
    private final PositionOnBoard positionOnBoard;
    private boolean selected;

    public MarbleViewModel(Marble marble, PositionOnBoard positionOnBoard,
	    boolean selected) {
	this.marble = marble;
	this.positionOnBoard = positionOnBoard;
	this.selected = selected;
    }

    public boolean isSelected() {
	return selected;
    }

    public boolean isNone() {
	return marble.isSameAs(Piece.NONE);
    }

    public Marble getMarble() {
	return marble;
    }

    public MarbleType getMarbleType() {
	return marble.getType();
    }

    public PositionOnBoard getPositionOnBoard() {
	return positionOnBoard;
    }

    public void updatePositionOnBoard(PositionOnBoard positionOnBoard) {
	this.positionOnBoard.setX(positionOnBoard.getX());
	this.positionOnBoard.setY(positionOnBoard.getY());
    }

    public Integer getMarbleId() {
	return marble.getId();
    }

    public void setSelected(boolean selected) {
	this.selected = selected;
    }
}
