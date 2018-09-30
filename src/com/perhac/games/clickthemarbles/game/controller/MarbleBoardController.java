package com.perhac.games.clickthemarbles.game.controller;

import java.util.*;

import com.perhac.games.clickthemarbles.game.GameConfig;
import com.perhac.games.clickthemarbles.game.MarbleGenerator;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleViewModel;
import com.perhac.games.clickthemarbles.model.*;

public class MarbleBoardController implements BoardController {

    protected MarbleBoard board;
    protected final MarbleGenerator generator;
    protected final GameConfig config = GameConfig.getInstance();
    private final Stack<String> undoStack = new Stack<String>();
    private static final MarbleBoardSerializer marbleBoardSerializer = new MarbleBoardSerializer();

    public MarbleBoardController() {
	board = new MarbleBoard();
	generator = new MarbleGenerator(config.getNumberOfColours());
    }

    @Override
    public MarbleBoardViewModel newGame() {
	board = new MarbleBoard();
	MarbleBoardViewModel viewModel = new MarbleBoardViewModel(board.SIZE);
	refillBoard(viewModel.toAdd);
	return viewModel;
    }

    protected void refillBoard(List<MarbleViewModel> toAdd) {
	while (!board.isFull()) {
	    Marble marble = generator.next();
	    PositionOnBoard position = board.nextAvailablePosition();
	    board.addPiece(marble, position);
	    toAdd.add(new MarbleViewModel(marble, position, false));
	}
    }

    @Override
    public MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard) {
	MarbleBoardViewModel viewModel = new MarbleBoardViewModel(
		getMarblesLeft());

	Set<PositionOnBoard> selection = determineSelection(positionOnBoard);
	viewModel.marblesLeft -= selection.size();

	if (selection.size() >= 2) {// chunk clicked, remove marbles

	    // first, take a snapshot of current state in order to support UNDOs
	    pushBoardStateHistory();

	    for (PositionOnBoard p : selection) {
		MarbleViewModel marbleToRemoveFromView = new MarbleViewModel(
			(Marble) board.getPieceAtPosition(p), p, true);
		viewModel.toRemove.add(marbleToRemoveFromView);
	    }

	    Set<PositionOnBoard> toBeMovedDown = board.removeMarbles(selection);
	    for (PositionOnBoard p : toBeMovedDown) {
		if (!board.isPositionAvailable(p)) {
		    MarbleViewModel marbleToMoveDown = new MarbleViewModel(
			    (Marble) board.getPieceAtPosition(p), p, false);
		    viewModel.toMove.add(marbleToMoveDown);
		}
	    }
	}

	viewModel.playable = !isGameOver();

	return viewModel;
    }

    private void pushBoardStateHistory() {
	undoStack.push(marbleBoardSerializer.boardToString(board));
    }

    public int getMarblesLeft() {
	return board.getPiecesPlaced();
    }

    // default visibility intentional
    Board getBoard() {
	return board;
    }

    /**
     * @param positionOnBoard
     *            position that would be clicked
     * @return list of positions that would also be selected
     */
    public Set<PositionOnBoard> determineSelection(
	    PositionOnBoard positionOnBoard) {
	Set<PositionOnBoard> selection = new HashSet<PositionOnBoard>();
	searchBoardForMatchingMarbles(positionOnBoard, selection,
		new HashSet<PositionOnBoard>());
	return selection;
    }

    /**
     * Recursive algorithm for determining which marbles are to be selected
     * 
     * @param positionOnBoard
     *            position (marble) selected
     * @param selection
     *            to store the marbles that should be selected (result)
     * @param visited
     *            to keep track of which positions have already been visited
     */
    private void searchBoardForMatchingMarbles(PositionOnBoard positionOnBoard,
	    Set<PositionOnBoard> selection, HashSet<PositionOnBoard> visited) {
	if (visited.contains(positionOnBoard))
	    return;
	Piece piece = board.getPieceAtPosition(positionOnBoard);
	visited.add(positionOnBoard);
	if (board.isPositionAvailable(positionOnBoard))
	    return;
	selection.add(positionOnBoard);
	PositionOnBoard[] adjacent = board
		.getAdjacentPositions(positionOnBoard);
	for (PositionOnBoard p : adjacent) {
	    if (piece.isSameAs(board.getPieceAtPosition(p))) {
		searchBoardForMatchingMarbles(p, selection, visited);
	    }
	}
    }

    /**
     * Will determine wheter there are any more clumps of N marbles on the board
     * that could be collected by the player.
     * 
     * @return true if the game is over - no more clumps of big enough size to
     *         be found on the board (or no more marbles on the board)
     */
    private boolean isGameOver() {
	// if there's only one marble left, there's no point checking for pairs
	if (board.getPiecesPlaced() < 2) {
	    return true;
	}

	return !board.canFindAdjacentMarblesOfSameType();
    }

    @Override
    public MarbleBoardViewModel getPreviousState() {
	if (undoStack.isEmpty()) {
	    return new MarbleBoardViewModel(board.getPiecesPlaced());
	}
	MarbleBoard previousBoard = marbleBoardSerializer
		.boardFromString(undoStack.pop());

	MarbleBoardViewModel viewModel = new MarbleBoardViewModel(
		previousBoard.getAvailablePositionCount());
	viewModel.skipAnimation = true;

	viewModel.toRemove.addAll(getAllMarblesViewModels());

	board = previousBoard;

	viewModel.toAdd.addAll(getAllMarblesViewModels());
	return viewModel;
    }

    private Collection<? extends MarbleViewModel> getAllMarblesViewModels() {
	List<MarbleViewModel> viewModels = new ArrayList<MarbleViewModel>(
		board.getPiecesPlaced());
	for (int x = 0; x < config.NUMBER_OF_COLUMNS; x++) {
	    for (int y = 0; y < config.NUMBER_OF_ROWS; y++) {
		PositionOnBoard positionOnBoard = new PositionOnBoard(x, y);
		if (!board.isPositionAvailable(positionOnBoard)) {
		    viewModels.add(new MarbleViewModel((Marble) board
			    .getPieceAtPosition(positionOnBoard),
			    positionOnBoard, false));
		}
	    }
	}
	return viewModels;
    }

	@Override
	public void reset() {
		//do nothing, fecking hack. solves tricky problem. bloody idiotic game design. 
	}

}
