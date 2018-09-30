package com.perhac.games.clickthemarbles.model;

import java.util.Stack;

import com.perhac.games.clickthemarbles.game.PointsCalculator;

public class Score {

    private int points;
    private int marblesCollected;
    private int bestPoints;
    private int bestMarblesCollected;
    private final Stack<Score> scoreUndoStack;
    private final int marblesTotal;

    public Score(int totalNumberOfMarblesOnBoard) {
	this.points = 0;
	this.marblesCollected = 0;
	this.scoreUndoStack = new Stack<Score>();
	this.marblesTotal = totalNumberOfMarblesOnBoard;
    }
    
    private Score(){
	this(0);
    }

    public int getPoints() {
	return points;
    }

    public int getMarblesCollected() {
	return marblesCollected;
    }

    public void add(final int marblesCollected) {
	if (marblesCollected < 0) {
	    throw new IllegalArgumentException("zero or marbles expected");
	}

	storeCurrentScoreForUndo();

	final int points = PointsCalculator.calculatePoints(marblesCollected);

	this.marblesCollected += marblesCollected;
	this.points += points;

	if (marblesCollected > bestMarblesCollected) {
	    this.bestMarblesCollected = marblesCollected;
	}

	if (points > bestPoints) {
	    this.bestPoints = points;
	}

    }

    private void storeCurrentScoreForUndo() {
	Score scoreForUndo = new Score();
	scoreForUndo.bestMarblesCollected = this.bestMarblesCollected;
	scoreForUndo.bestPoints = this.bestPoints;
	scoreForUndo.marblesCollected = this.marblesCollected;
	scoreForUndo.points = this.points;
	scoreUndoStack.push(scoreForUndo);
    }

    public void undo() {
	if (!scoreUndoStack.empty()) {
	    copyFrom(scoreUndoStack.pop());
	}
    }

    private void copyFrom(Score score) {
	this.bestMarblesCollected = score.getBestMarblesCollected();
	this.bestPoints = score.getBestPoints();
	this.marblesCollected = score.getMarblesCollected();
	this.points = score.getPoints();
    }

    public int getBestPoints() {
	return bestPoints;
    }

    public int getBestMarblesCollected() {
	return bestMarblesCollected;
    }

    @Override
    public String toString() {
	return "Score [points=" + points + ", marblesCollected="
		+ marblesCollected + "]";
    }

    public int getMarblesTotal() {
	return marblesTotal;
    }

}
