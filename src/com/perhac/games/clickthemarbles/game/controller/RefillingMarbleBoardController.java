package com.perhac.games.clickthemarbles.game.controller;

import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public class RefillingMarbleBoardController extends MarbleBoardController {

    @Override
    public MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard) {
	MarbleBoardViewModel viewModel = super.clickMarble(positionOnBoard);
	super.refillBoard(viewModel.toAdd);
	return viewModel;
    }

}
