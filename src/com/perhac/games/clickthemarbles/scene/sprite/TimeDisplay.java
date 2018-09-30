package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.image.CoreFont;
import pulpcore.image.filter.Filter;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.game.PulpCoreStopwatch;

public class TimeDisplay extends Label {

    private static final int UPDATE_EACH_MILLIS = 500;
    private PulpCoreStopwatch stopwatch;
    private int timeSinceLastRedraw = UPDATE_EACH_MILLIS;

    public TimeDisplay(PulpCoreStopwatch stopwatch, CoreFont font, int x, int y, Filter filter) {
	this(font, stopwatch.toString(), x, y);
	this.setFilter(filter);
	this.stopwatch = stopwatch;
    }

    private TimeDisplay(CoreFont font, String text, int x, int y) {
	super(font, text, x, y);
    }

    @Override
    public void update(int elapsedTime) {
	timeSinceLastRedraw += elapsedTime;
	if (timeSinceLastRedraw > UPDATE_EACH_MILLIS) {
	    this.setText(stopwatch.toString());
	    super.update(elapsedTime);
	    timeSinceLastRedraw = 0;
	}
    }

}
