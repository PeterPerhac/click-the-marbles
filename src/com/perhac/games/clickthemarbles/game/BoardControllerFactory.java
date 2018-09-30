package com.perhac.games.clickthemarbles.game;

import com.perhac.games.clickthemarbles.game.controller.BoardController;
import com.perhac.games.clickthemarbles.game.controller.MarbleBoardController;
import com.perhac.games.clickthemarbles.game.controller.RefillingMarbleBoardController;
import com.perhac.games.clickthemarbles.game.controller.SelectFirstBoardController;

public class BoardControllerFactory {

    private static final GameConfig config = GameConfig.getInstance();

    public static BoardController getBoardController() {
	MarbleBoardController controller;

	switch (config.getGameMode()) {
	case ENDLESS: {
	    controller = new RefillingMarbleBoardController();
	    break;
	}
	case CLICK_ALL:
	default:
	    controller = new MarbleBoardController();
	}
	return new SelectFirstBoardController(controller);
    }
}
