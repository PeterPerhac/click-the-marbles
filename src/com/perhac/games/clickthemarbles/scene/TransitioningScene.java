package com.perhac.games.clickthemarbles.scene;

import pulpcore.image.Colors;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;

public abstract class TransitioningScene extends Scene2D {

    private static final int DURATION = 500;
    private final static FilledSprite whiteness = new FilledSprite(Colors.WHITE);
    private final static FilledSprite blackness = new FilledSprite(Colors.BLACK);

    protected void fadeInFromBlack() {
	blackness.alpha.set(255);
	this.add(blackness);
	blackness.alpha.animateTo(0, DURATION);
    }

    protected void fadeInFromWhite() {
	whiteness.alpha.set(255);
	this.add(whiteness);
	whiteness.alpha.animateTo(0, DURATION);
    }

    protected void fadeOutToBlack() {
	blackness.alpha.set(0);
	this.add(blackness);
	blackness.alpha.animateTo(255, DURATION);
    }

    protected void fadeOutToWhite() {
	whiteness.alpha.set(0);
	this.add(whiteness);
	whiteness.alpha.animateTo(255, DURATION);
    }
}
