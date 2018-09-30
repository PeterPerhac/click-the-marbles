package com.perhac.games.clickthemarbles.game;

import pulpcore.CoreSystem;

public class PulpCoreStopwatch {

    private long startMillis = 0;
    private long startPauseMillis = 0;
    private boolean paused = true;
    private long lastMilis;

    public PulpCoreStopwatch() {
    }

    public boolean isPaused() {
	return paused;
    }

    public void setPaused(boolean paused) {
	this.paused = paused;
    }

    public void start() {
	setPaused(false);
	startMillis = CoreSystem.getTimeMillis();
    }

    public void pause() {
	setPaused(true);
	startPauseMillis = CoreSystem.getTimeMillis();
    }

    public void resume() {
	if (isPaused()) {
	    setPaused(false);
	    startMillis += (CoreSystem.getTimeMillis() - startPauseMillis);
	}
    }

    public long getMillis() {
	if (!paused) {
	    lastMilis = CoreSystem.getTimeMillis() - startMillis;
	}
	return lastMilis;
    }

    @Override
    public String toString() {
	int secondsTotal = (int) (getMillis() / 1000);
	int hours = secondsTotal / 3600;
	int minutes = (secondsTotal - (hours * 3600)) / 60;
	int seconds = secondsTotal - (hours * 3600) - (minutes * 60);

	if (hours > 0) {
	    return String.format("%dh : %dm : %ds", new Object[] { hours,
		    minutes, seconds });
	} else {
	    return String
		    .format("%dm : %ds", new Object[] { minutes, seconds });
	}
    }

}
