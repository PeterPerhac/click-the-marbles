package com.perhac.games.clickthemarbles.game;

public final class PointsCalculator {

    private PointsCalculator() {
    }

    private static final int BASE_MARBLE_VALUE = 10;

    public static int calculatePoints(int marblesCollected) {

	if (marblesCollected < 0) {
	    throw new IllegalArgumentException(
		    "Calculating points for less than 0 marbles");
	}

	int points = 0;

	while (marblesCollected > 0) {
	    points += (BASE_MARBLE_VALUE * marblesCollected);
	    marblesCollected--;
	}

	return points;
    }

}
