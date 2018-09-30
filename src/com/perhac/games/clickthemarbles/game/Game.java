package com.perhac.games.clickthemarbles.game;

import pulpcore.scene.Scene2D;

import com.perhac.games.clickthemarbles.game.controller.BoardController;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;
import com.perhac.games.clickthemarbles.model.Score;

public class Game {

    private Score score;
    private final PulpCoreStopwatch stopwatch;
    private final BoardController controller;

    public Game() {
	stopwatch = new PulpCoreStopwatch();
	controller = BoardControllerFactory.getBoardController();
    }

    public MarbleBoardViewModel newGame() {
	stopwatch.start();
	MarbleBoardViewModel newGame = controller.newGame();
	score = new Score(newGame.marblesLeft);
	return newGame;
    }

    public MarbleBoardViewModel marbleClicked(PositionOnBoard positionOnBoard) {
	MarbleBoardViewModel mbvm = controller.clickMarble(positionOnBoard);
	int marblesRemovedByClicking = mbvm.toRemove.size();
	if (marblesRemovedByClicking > 0) {
	    score.add(marblesRemovedByClicking);
	}
	return mbvm;
    }

    public PulpCoreStopwatch getStopWatch() {
	return stopwatch;
    }

    public Score getScore() {
	return score;
    }

    public void stopTheWatch() {
	stopwatch.pause();
    }

    public MarbleBoardViewModel undo() {
	score.undo();
	if (stopwatch.isPaused()) {
	    stopwatch.resume();
	}
	return controller.getPreviousState();
    }

    //fuck me for this too
	public BoardController getController() {
		return controller;
	}

}
