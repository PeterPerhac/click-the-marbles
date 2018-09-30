package com.perhac.games.clickthemarbles.game.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.model.Board;
import com.perhac.games.clickthemarbles.model.Marble;
import com.perhac.games.clickthemarbles.model.MarbleType;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public class MarbleBoardControllerTest {

    private MarbleBoardController controller;

    @Before
    public void setUpBeforeEachTest() {
	controller = new TestMarbleBoardController();
    }

    @Test
    public void newGame_happyPath() {
	// Act
	controller.newGame();
	// Assert
	Board changedGameBoard = controller.getBoard();
	assertTrue(changedGameBoard.isFull());

    }

    @Test
    public void determineSelection_happyPath() {
	Board board = controller.getBoard();
	Marble selectedPiece = new Marble(MarbleType.AQUA);

	board.addPiece(selectedPiece, new PositionOnBoard(0, 0));
	board.addPiece(selectedPiece, new PositionOnBoard(10, 0));
	board.addPiece(selectedPiece, new PositionOnBoard(0, 10));
	board.addPiece(selectedPiece, new PositionOnBoard(10, 10));

	board.addPiece(selectedPiece, new PositionOnBoard(5, 5));// sel 1
	board.addPiece(selectedPiece, new PositionOnBoard(4, 5));// sel 2
	board.addPiece(new Marble(MarbleType.RED), new PositionOnBoard(5, 6));

	board.addPiece(selectedPiece, new PositionOnBoard(4, 4));// sel 3

	Set<PositionOnBoard> selection = controller
		.determineSelection(new PositionOnBoard(5, 5));

	assertEquals(3, selection.size());
	for (PositionOnBoard p : selection) {
	    assertTrue("Unwanted marble type in selection",
		    selectedPiece.isSameAs(board.getPieceAtPosition(p)));
	}
    }

    @Test
    public void determineSelection_emptyPositionClicked() {
	// Arrange
	Board board = controller.getBoard();
	Marble marble = new Marble(MarbleType.RED);
	board.addPiece(marble, new PositionOnBoard(5, 5));

	// Act
	Set<PositionOnBoard> selection = controller
		.determineSelection(new PositionOnBoard(1, 1));

	// Assert
	assertEquals(0, selection.size());
    }

    private class TestMarbleBoardController extends MarbleBoardController {

	@Override
	public MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard) {
	    return new MarbleBoardViewModel(100);
	}

    }
}
