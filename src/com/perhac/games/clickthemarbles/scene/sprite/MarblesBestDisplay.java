package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.image.CoreFont;
import pulpcore.image.filter.Filter;

import com.perhac.games.clickthemarbles.model.Score;

public class MarblesBestDisplay extends PointsDisplay {

    public MarblesBestDisplay(CoreFont font, Score score, int x, int y, Filter filter) {
	super(font, score, x, y, filter);
    }

    @Override
    protected int getValue() {
	return score.getBestMarblesCollected();
    }

}
