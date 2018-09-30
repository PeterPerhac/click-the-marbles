package com.perhac.games.clickthemarbles.model;

import java.util.ArrayList;
import java.util.List;

import com.perhac.games.clickthemarbles.game.GameConfig;

public class Board {

    protected final GameConfig gameConfig = GameConfig.getInstance();
    private final Piece[][] positions = new Piece[gameConfig.NUMBER_OF_COLUMNS][gameConfig.NUMBER_OF_ROWS];

    private int piecesPlaced = 0;

    public final int SIZE;

    public Board() {
	SIZE = gameConfig.NUMBER_OF_COLUMNS * gameConfig.NUMBER_OF_ROWS;
	for (int x = 0; x < gameConfig.NUMBER_OF_COLUMNS; x++)
	    for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++)
		setPieceAtPosition(Piece.NONE, new PositionOnBoard(x, y));
    }

    public void addPiece(Piece piece, PositionOnBoard positionOnBoard) {
	if (!isPositionAvailable(positionOnBoard)) {
	    throw new IllegalArgumentException(
		    "Adding adding piece to unavailable possition");
	}
	if (Piece.NONE.equals(piece)) {
	    throw new IllegalArgumentException(
		    "Adding an 'empty' piece to the board. Consider using removePiece method instead.");
	}
	setPieceAtPosition(piece, positionOnBoard);
	incrementPiecesPlaced();
    }

    public PositionOnBoard[] getAdjacentPositions(
	    PositionOnBoard positionOnBoard) {
	List<PositionOnBoard> adjacent = new ArrayList<PositionOnBoard>(4);
	if (!positionOnBoard.isEdgeLeft()) {
	    adjacent.add(new PositionOnBoard(positionOnBoard.getX() - 1,
		    positionOnBoard.getY()));
	}
	if (!positionOnBoard.isEdgeRight()) {
	    adjacent.add(new PositionOnBoard(positionOnBoard.getX() + 1,
		    positionOnBoard.getY()));
	}
	if (!positionOnBoard.isEdgeBottom()) {
	    adjacent.add(new PositionOnBoard(positionOnBoard.getX(),
		    positionOnBoard.getY() - 1));
	}
	if (!positionOnBoard.isEdgeTop()) {
	    adjacent.add(new PositionOnBoard(positionOnBoard.getX(),
		    positionOnBoard.getY() + 1));
	}
	return adjacent.toArray(new PositionOnBoard[adjacent.size()]);
    }

    public boolean isFull() {
	return piecesPlaced == SIZE;
    }

    public PositionOnBoard nextAvailablePosition() {
	if (this.isFull()) {
	    throw new IllegalArgumentException(
		    "Board has no more available positions");
	}
	return findAvailablePosition();
    }

    public Piece removePiece(PositionOnBoard positionOnBoard) {
	if (isPositionAvailable(positionOnBoard)) {
	    throw new IllegalArgumentException(
		    "Removing piece from a position that was already available");
	}
	Piece removed = getPieceAtPosition(positionOnBoard);
	setPieceAtPosition(Piece.NONE, positionOnBoard);
	decrementPiecesPlaced();
	return removed;
    }

    public Piece getPieceAtPosition(PositionOnBoard positionOnBoard) {
	return positions[positionOnBoard.getX()][positionOnBoard.getY()];
    }

    public boolean isPositionAvailable(PositionOnBoard positionOnBoard) {
	return Piece.NONE.equals(getPieceAtPosition(positionOnBoard));
    }

    public int getPiecesPlaced() {
	return piecesPlaced;
    }

    // ////////////////
    // private methods
    // ////////////////
    private PositionOnBoard findAvailablePosition() {
	for (int x = 0; x < gameConfig.NUMBER_OF_COLUMNS; x++) {
	    for (int y = 0; y < gameConfig.NUMBER_OF_ROWS; y++) {
		PositionOnBoard availablePosition = new PositionOnBoard(x, y);
		if (isPositionAvailable(availablePosition)) {
		    return availablePosition;
		}
	    }
	}
	throw new RuntimeException(
		"There should have been at least one available position found on the board");
    }

    private void setPieceAtPosition(Piece piece, PositionOnBoard positionOnBoard) {
	positions[positionOnBoard.getX()][positionOnBoard.getY()] = piece;
    }

    private void incrementPiecesPlaced() {
	assert piecesPlaced < SIZE;
	piecesPlaced++;
    }

    private void decrementPiecesPlaced() {
	assert piecesPlaced > 0;
	piecesPlaced--;
    }

    public int getAvailablePositionCount() {
	return SIZE - piecesPlaced;
    }

}
