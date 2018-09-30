package com.perhac.games.clickthemarbles.game.viewmodel;

import java.util.ArrayList;
import java.util.List;

public final class MarbleBoardViewModel {

    public final List<MarbleViewModel> toRemove;
    public final List<MarbleViewModel> toAdd;
    public final List<MarbleViewModel> toMove;
    public int marblesLeft;
    public int possibleScore;
    public boolean playable;
    public List<Integer> selectedMarbles;
    public boolean skipAnimation;

    public MarbleBoardViewModel(int marblesLeft) {
	toAdd = new ArrayList<MarbleViewModel>();
	toRemove = new ArrayList<MarbleViewModel>();
	toMove = new ArrayList<MarbleViewModel>();
	selectedMarbles = new ArrayList<Integer>();
	this.marblesLeft = marblesLeft;
	possibleScore = 0;
	playable = true;
	skipAnimation = false;
    }

}
