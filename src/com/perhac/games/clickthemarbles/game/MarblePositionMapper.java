package com.perhac.games.clickthemarbles.game;

import java.awt.Point;

import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public final class MarblePositionMapper {

    private MarblePositionMapper() {
    } // don't let instantiate

    private static final int MARGIN_TOP = 110;
    private static final int MARGIN_LEFT = 40;
    private static final int ROWS = GameConfig.getInstance().NUMBER_OF_ROWS;

    public static Point mapToScreenPosition(PositionOnBoard positionOnBoard) {
	Point p = new Point();
	p.setLocation(MARGIN_LEFT + (positionOnBoard.getX() * 30), MARGIN_TOP
		+ (((ROWS - positionOnBoard.getY()) * 30)));
	return p;
    }

}
