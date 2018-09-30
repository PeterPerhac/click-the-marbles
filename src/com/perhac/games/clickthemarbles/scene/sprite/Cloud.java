package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.animation.Easing;
import pulpcore.animation.Timeline;
import pulpcore.image.CoreImage;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.ImageSprite;

public class Cloud extends ImageSprite {

    private static final double SLACK = 20;

    private Cloud(Scene2D scene, double y, int duration, int delay,
	    boolean flipped) {
	super(CoreImage.load("cloud.png"), 0, 0);
	this.setAnchor(0, 0.5);
	double fromX = 0 - this.width.get() - SLACK;
	double toX = 800 + SLACK;
	this.setLocation(fromX, y);
	if (flipped) {
	    this.getImage().flip();
	}
	Timeline t = new Timeline();
	t.animate(this.x, fromX, toX, duration, Easing.NONE, delay);
	t.scale(this, this.width.get(), this.height.get(),
		this.width.get() / 2, this.height.get() / 2, duration / 2,
		Easing.NONE, delay);
	t.scale(this, this.width.get() / 2, this.height.get() / 2,
		this.width.get(), this.height.get(), duration / 2, Easing.NONE,
		delay + (duration / 2));
	t.loopForever();
	scene.addTimeline(t);
    }

    public static void newCloud(Scene2D scene, int y, int duration, int delay,
	    boolean flipped) {
	scene.add(new Cloud(scene, y, duration, delay, flipped));
    }

    public static void newCloud(Scene2D scene, int y, int duration, int delay) {
	scene.add(new Cloud(scene, y, duration, delay, false));
    }
}