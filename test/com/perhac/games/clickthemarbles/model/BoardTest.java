package com.perhac.games.clickthemarbles.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    private Board board;

    @Before
    public void setUpBeforeEachTest() {
	board = new Board();
    }

    @Test
    public void addPiece_happyPath() {
	// Arrange
	int marblesPlacedInitially = board.getPiecesPlaced();
	Piece marble = new Marble(MarbleType.RED);
	PositionOnBoard positionOnBoard = board.nextAvailablePosition();

	// Act
	board.addPiece(marble, positionOnBoard);

	// Assert
	assertTrue(board.getPiecesPlaced() == (marblesPlacedInitially + 1));
	assertEquals(marble, board.getPieceAtPosition(positionOnBoard));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPiece_NoPiece() {
	Piece marble = Piece.NONE;
	PositionOnBoard positionOnBoard = board.nextAvailablePosition();

	board.addPiece(marble, positionOnBoard);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPiece_PositionNotAvailable() {
	// Arrange
	Piece marble = new Marble(MarbleType.RED);
	PositionOnBoard positionOnBoard = board.nextAvailablePosition();
	board.addPiece(marble, positionOnBoard); // occupy position

	// Act
	board.addPiece(marble, positionOnBoard);
    }

    @Test
    public void removePiece_happyPath() {
	// Arrange
	Piece marble = new Marble(MarbleType.RED);
	PositionOnBoard positionOnBoard = board.nextAvailablePosition();
	board.addPiece(marble, positionOnBoard);
	int marblesPlacedInitially = board.getPiecesPlaced();

	// Act
	board.removePiece(positionOnBoard);

	// Assert
	assertTrue(board.getPiecesPlaced() == (marblesPlacedInitially - 1));
	assertEquals(Piece.NONE, board.getPieceAtPosition(positionOnBoard));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removePiece_fromAnEmptyPosition() {
	board.removePiece(board.nextAvailablePosition());
    }

    @Test
    public void isFull_isFull() {
	fillTheBoard();
	assertTrue(board.isFull());
    }

    @Test
    public void isFull_isEmpty() {
	assertFalse(board.isFull());
    }

    @Test
    public void isFull_hasSomePieces_returnsFalse() {
	board.addPiece(new Marble(MarbleType.RED), new PositionOnBoard(5, 5));
	assertFalse(board.isFull());
    }

    @Test
    public void nextAvailablePossition_happyPath() {
	PositionOnBoard availablePosition = board.nextAvailablePosition();
	assertTrue(board.isPositionAvailable(availablePosition));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextAvailablePosition_noAvailablePositions() {
	fillTheBoard();
	board.nextAvailablePosition();
    }

    @Test
    public void getAdjacentPositions_happyPath() {
	// Arrange
	PositionOnBoard positionWellInside = new PositionOnBoard(5, 5);

	// Act
	PositionOnBoard[] adjacent = board
		.getAdjacentPositions(positionWellInside);

	// Assert
	assertEquals(4, adjacent.length);
	assertAllAdjacentReallyAdjacent(positionWellInside, adjacent);
    }

    @Test
    public void getAdjacentPositions_cornerPosition() {
	// Arrange
	PositionOnBoard cornerPosition = new PositionOnBoard(0, 0);

	// Act
	PositionOnBoard[] adjacent = board.getAdjacentPositions(cornerPosition);

	// Assert
	assertEquals(2, adjacent.length);
	assertAllAdjacentReallyAdjacent(cornerPosition, adjacent);
    }

    @Test
    public void getAdjacentPositions_sidePosition() {
	// Arrange
	PositionOnBoard sidePosition = new PositionOnBoard(5, 0);

	// Act
	PositionOnBoard[] adjacent = board.getAdjacentPositions(sidePosition);

	// Assert
	assertEquals(3, adjacent.length);
	assertAllAdjacentReallyAdjacent(sidePosition, adjacent);
    }

    // /////
    // utility methods
    // /////
    private void fillTheBoard() {
	while (board.getPiecesPlaced() < board.SIZE) {
	    board.addPiece(new Marble(MarbleType.RED),
		    board.nextAvailablePosition());
	}
    }

    private void assertAllAdjacentReallyAdjacent(PositionOnBoard position,
	    PositionOnBoard[] adjacent) {
	for (PositionOnBoard p : adjacent) {
	    int distance = Math.abs(position.getX() - p.getX())
		    + Math.abs(position.getY() - p.getY());
	    assertEquals(1, distance);
	}
    }

}
