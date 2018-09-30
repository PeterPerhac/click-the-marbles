package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.image.CoreFont;
import pulpcore.image.filter.Filter;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.model.Score;

public abstract class PointsDisplay extends Label {

    private static final int MIN_ELAPSED_TIME = 30;
    protected Score score;
    private int pointsDisplayed = 0;
    private int inc = 1;
    private int accumulator = 0;

    protected abstract int getValue();

    public PointsDisplay(CoreFont font, Score score, int x, int y, Filter filter) {
	this(font, "", x, y);
	this.score = score;
	setFilter(filter);
    }

    private PointsDisplay(CoreFont font, String text, int x, int y) {
	super(font, text, x, y);
    }

    @Override
    public void update(int elapsedTime) {
	if (!shouldUpdateNow(elapsedTime)) {
	    return;
	}
	int valueToReach = getValue();
	if (pointsDisplayed < valueToReach) {
	    pointsDisplayed += inc++;
	    if (pointsDisplayed > valueToReach) {
		pointsDisplayed = valueToReach;
		inc = 1;
	    }
	}

	if (pointsDisplayed > valueToReach) {
	    pointsDisplayed -= inc++;
	    if (pointsDisplayed < valueToReach) {
		pointsDisplayed = valueToReach;
		inc = 1;
	    }
	}
	this.setText(String.valueOf(pointsDisplayed));
	super.update(elapsedTime);
    }

    private boolean shouldUpdateNow(int elapsedTime) {
	boolean shouldUpdate = false;
	accumulator += elapsedTime;
	if (accumulator >= MIN_ELAPSED_TIME) {
	    shouldUpdate = true;
	    accumulator = 0;
	}
	return shouldUpdate;
    }
}
