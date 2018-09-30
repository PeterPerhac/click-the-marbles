package com.perhac.games.clickthemarbles.model;

import com.perhac.games.clickthemarbles.game.GameConfig;

public class PositionOnBoard {

    private final GameConfig config = GameConfig.getInstance();

    private int x;
    private int y;

    public PositionOnBoard(int x, int y) {
	setX(x);
	setY(y);
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	if (x < 0 || x >= config.NUMBER_OF_COLUMNS) {
	    throw new IllegalArgumentException(
		    "Can't place marble outside the board. X can't be " + x);
	}
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	if (y < 0 || y >= config.NUMBER_OF_ROWS) {
	    throw new IllegalArgumentException(
		    "Can't place marble outside the board. Y can't be " + y);
	}
	this.y = y;
    }

    public boolean isEdgeTop() {
	return getY() == (config.NUMBER_OF_ROWS - 1);
    }

    public boolean isEdgeBottom() {
	return getY() == 0;
    }

    public boolean isEdgeRight() {
	return getX() == (config.NUMBER_OF_COLUMNS - 1);
    }

    public boolean isEdgeLeft() {
	return getX() == 0;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + x;
	result = prime * result + y;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PositionOnBoard other = (PositionOnBoard) obj;
	return (x == other.x && y == other.y);
    }

}
