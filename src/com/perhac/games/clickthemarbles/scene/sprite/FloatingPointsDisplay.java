package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

public class FloatingPointsDisplay extends Group {

    private static final int MARGIN = 2;
    private static final int DOUBLE_MARGIN = 4;
    private int pointsOnDisplay = 0;
    private int pointsToDisplay = 0;
    private final Label pointsLabel;
    private Sprite darkBackground;

    public FloatingPointsDisplay() {
	pointsLabel = new Label(CoreFont.load("points.display.font.png"), "0",
		0, 0);
	darkBackground = new FilledSprite(x.get() - MARGIN, y.get() - MARGIN,
		pointsLabel.width.get(), pointsLabel.height.get()
			+ DOUBLE_MARGIN, Colors.BLACK);
	darkBackground.alpha.set(128);
	add(darkBackground);
	add(pointsLabel);
	alpha.set(0);
    }

    @Override
    public void update(int elapsedTime) {
	if (pointsToDisplay != pointsOnDisplay) {
	    pointsOnDisplay = pointsToDisplay;
	    pointsLabel.setText("" + pointsOnDisplay);
	    darkBackground.width.set(pointsLabel.width.get() + DOUBLE_MARGIN);
	}
	super.update(elapsedTime);
    }

    public void setPointsToDisplay(int pointsToDisplay) {
	this.pointsToDisplay = pointsToDisplay;
    }

}
