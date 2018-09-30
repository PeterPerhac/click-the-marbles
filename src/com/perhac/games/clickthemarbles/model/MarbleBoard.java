package com.perhac.games.clickthemarbles.model;

import java.util.*;

public class MarbleBoard extends Board {

    /**
     * This method will remove all marbles in the positions specified by the
     * argument, followed by pushing any marbles that are left
     * "hanging in mid-air" down (like gravitational force). Firstly, all the
     * marbles are removed, secondly, all the necessary marbles are
     * repositioned.
     * 
     * @param positionsToClear
     *            positions on the board from which marbles are to be removed
     * @return a set of positions on the board that are now occupied by marbles
     *         that had to be repositioned
     */
    public Set<PositionOnBoard> removeMarbles(
	    Set<PositionOnBoard> positionsToClear) {
	Set<PositionOnBoard> changedPositions = new HashSet<PositionOnBoard>();

	for (PositionOnBoard p : positionsToClear) {
	    this.removePiece(p);
	}

	findChangedPositions(changedPositions);
	if (GameMode.CLICK_ALL.equals(gameConfig.getGameMode())) {
	    rakeLeft(changedPositions);
	}

	return changedPositions;
    }

    private void rakeLeft(Set<PositionOnBoard> changedPositions) {
	int emptyColumnCount = countEmptyColumns();
	if (getPiecesPlaced() == 0 || emptyColumnCount == 0) // board empty or
							     // full
	    return;

	final int COLUMNS_TO_RAKE = gameConfig.NUMBER_OF_COLUMNS
		- emptyColumnCount;
	for (int x = 0; x < COLUMNS_TO_RAKE; x++) {
	    if (isColumnEmpty(x)) {
		int grabFromColumn = x + 1;
		while (isColumnEmpty(x)) {
		    moveColumnIntoColumn(x, grabFromColumn);
		    grabFromColumn++;
		}
		for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++) {
		    PositionOnBoard sourcePosition = new PositionOnBoard(x, y);
		    if (!isPositionAvailable(sourcePosition)) {
			changedPositions.add(sourcePosition);
		    } else {
			break;
		    }
		}
	    }
	}
    }

    private void moveColumnIntoColumn(int xTarget, int xSource) {
	if (isColumnEmpty(xSource))
	    return;
	for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++) {
	    PositionOnBoard sourcePosition = new PositionOnBoard(xSource, y);
	    if (!isPositionAvailable(sourcePosition)) {
		Piece piece = removePiece(sourcePosition);
		addPiece(piece, new PositionOnBoard(xTarget, y));
	    }
	}
    }

    private int countEmptyColumns() {
	int sum = 0;
	for (int x = gameConfig.NUMBER_OF_COLUMNS - 1; x >= 0; x--) {
	    if (isColumnEmpty(x)) {
		sum++;
	    }
	}
	return sum;
    }

    private boolean isColumnEmpty(final int x) {
	// column is empty when the bottom-most position is empty
	return isPositionAvailable(new PositionOnBoard(x, 0));
    }

    private void findChangedPositions(Set<PositionOnBoard> changedPositions) {
	for (int x = 0; x < gameConfig.NUMBER_OF_COLUMNS; x++) {
	    boolean anythingMovedDown;
	    do {
		anythingMovedDown = false;
		for (int y = gameConfig.NUMBER_OF_ROWS - 1; y > 0; y--) {
		    PositionOnBoard current = new PositionOnBoard(x, y);
		    PositionOnBoard southOfCurrent = new PositionOnBoard(x,
			    y - 1);
		    if (!isPositionAvailable(current)
			    && isPositionAvailable(southOfCurrent)) {
			// move and say it's moved
			Piece removed = this.removePiece(current);
			this.addPiece(removed, southOfCurrent);
			changedPositions.add(southOfCurrent);
			anythingMovedDown = true;
		    }
		}
	    } while (anythingMovedDown);
	}
    }

    public boolean canFindAdjacentMarblesOfSameType() {
	for (int x = 0; x < gameConfig.NUMBER_OF_COLUMNS; x++) {
	    for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++) {
		if (canBeCleared(x, y)) {
		    return true;
		}
	    }
	}
	return false;
    }

    private boolean canBeCleared(int x, int y) {
	PositionOnBoard position = new PositionOnBoard(x, y);
	if (!isPositionAvailable(position)) {
	    Piece piece = getPieceAtPosition(position);
	    PositionOnBoard[] adjacent = getAdjacentPositions(position);
	    for (PositionOnBoard p : adjacent) {
		if (piece.isSameAs(getPieceAtPosition(p))) {
		    return true;
		}
	    }
	}
	return false;
    }

    public Collection<Marble> getAllMarbles() {
	List<Marble> allMarbles = new ArrayList<Marble>(this.getPiecesPlaced());
	for (int x = 0; x < gameConfig.NUMBER_OF_COLUMNS; x++) {
	    for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++) {
		PositionOnBoard positionOnBoard = new PositionOnBoard(x, y);
		if (!isPositionAvailable(positionOnBoard)) {
		    allMarbles
			    .add((Marble) getPieceAtPosition(positionOnBoard));
		}
	    }
	}
	return allMarbles;
    }
}
