package com.perhac.games.clickthemarbles.game.controller;

import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public interface BoardController {

    MarbleBoardViewModel newGame();

    /**
     * @param positionOnBoard
     *            which position on the game board was clicked
     * @return view model to be rendered by the GameScene
     */
    MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard);

    MarbleBoardViewModel getPreviousState();

	void reset();

}
