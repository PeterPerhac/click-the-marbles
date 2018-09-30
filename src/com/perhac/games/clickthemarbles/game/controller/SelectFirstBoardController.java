package com.perhac.games.clickthemarbles.game.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.perhac.games.clickthemarbles.game.GameConfig;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;

public class SelectFirstBoardController implements BoardController {

    private final MarbleBoardController proxiedController;
    private PositionOnBoard lastClickedPosition;
    
    private final GameConfig config = GameConfig.getInstance();

    public SelectFirstBoardController(MarbleBoardController toProxy) {
	this.proxiedController = toProxy;
	this.lastClickedPosition = null;
    }

    @Override
    public MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard) {
    	
    	if (!config.isSelectFirst()){
    		return proxiedController.clickMarble(positionOnBoard);
    	}
    	
	Set<PositionOnBoard> selection = proxiedController
		.determineSelection(positionOnBoard);
	if (selection.contains(lastClickedPosition)) {
	    lastClickedPosition = null;
	    return proxiedController.clickMarble(positionOnBoard);
	} else {
	    lastClickedPosition = positionOnBoard;
	    // new MarbleBoardViewModel means "no changes to the board state"
	    MarbleBoardViewModel marbleBoardViewModel = new MarbleBoardViewModel(
		    proxiedController.getMarblesLeft());
	    marbleBoardViewModel.selectedMarbles = extractIdsFromSelection(selection);
	    return marbleBoardViewModel;
	}
    }

    private List<Integer> extractIdsFromSelection(Set<PositionOnBoard> selection) {
	List<Integer> ids = new ArrayList<Integer>(selection.size());
	for (PositionOnBoard p : selection) {
	    // FUCK ME FOR THIS
	    ids.add(proxiedController.getBoard().getPieceAtPosition(p).getId());
	}
	return ids;
    }

    @Override
    public MarbleBoardViewModel newGame() {
	return proxiedController.newGame();
    }

    @Override
    public MarbleBoardViewModel getPreviousState() {
	return proxiedController.getPreviousState();
    }

    //FUCK ME FOR THIS TOO
    public void reset(){
    	this.lastClickedPosition = null;
    }
    
}
