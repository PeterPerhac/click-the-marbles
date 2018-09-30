package com.perhac.games.clickthemarbles.game;

import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.image.filter.DropShadow;
import pulpcore.image.filter.Filter;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.model.GameMode;
import com.perhac.games.clickthemarbles.model.Score;
import com.perhac.games.clickthemarbles.scene.sprite.*;

public class HUDBuilder {

    private static final int TOP = 35;
    private static final int LEFT = 140;
    private static final int LEFT_2 = 360;
    private static final int PADDING = 10;
    private static final Filter LABEL_EFFECTS = new DropShadow(2, 2,
	    Colors.rgba(0, 220), 1);

    private HUDBuilder() {
    }

    public static Group buildHUD(GameMode currentGameMode, CoreFont font,
	    Score score, PulpCoreStopwatch stopwatch) {
	Group hud = new Group();
	Label label1;
	Label label2;
	if (GameMode.CLICK_ALL.equals(currentGameMode)) {
	    hud.add(new TimeDisplay(stopwatch, font, LEFT, 65, LABEL_EFFECTS));

	    label1 = new Label(font, "Points:", LEFT, TOP);
	    label1.setFilter(LABEL_EFFECTS);
	    hud.add(label1);
	    hud.add(new PointsTotalDisplay(font, score, LEFT
		    + label1.width.getAsInt() + PADDING, TOP, LABEL_EFFECTS));

	    label2 = new Label(font, "Marbles left:", LEFT_2, TOP);
	    label2.setFilter(LABEL_EFFECTS);
	    hud.add(label2);
	    hud.add(new MarblesLeftDisplay(font, score, LEFT_2
		    + label2.width.getAsInt() + PADDING, TOP, LABEL_EFFECTS));
	} else {
	    label1 = new Label(font, "Best score:", LEFT, TOP);
	    label1.setFilter(LABEL_EFFECTS);
	    hud.add(label1);
	    hud.add(new PointsBestDisplay(font, score, LEFT
		    + label1.width.getAsInt() + PADDING, TOP, LABEL_EFFECTS));
	    label2 = new Label(font, "Best shot:", LEFT_2, TOP);
	    label2.setFilter(LABEL_EFFECTS);
	    hud.add(label2);
	    hud.add(new MarblesBestDisplay(font, score, LEFT_2
		    + label2.width.getAsInt() + PADDING, TOP, LABEL_EFFECTS));
	}
	return hud;
    }

}
