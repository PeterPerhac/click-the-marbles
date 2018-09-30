package com.perhac.games.clickthemarbles.scene.sprite;

import java.awt.Point;

import pulpcore.image.CoreImage;
import pulpcore.image.filter.Filter;
import pulpcore.image.filter.Glow;
import pulpcore.sprite.ImageSprite;

import com.perhac.games.clickthemarbles.game.MarblePositionMapper;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleViewModel;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;
import com.perhac.games.clickthemarbles.scene.GameScene;

public class MarbleSprite extends ImageSprite {

    private final GameScene game;
    private final MarbleViewModel viewModel;

    private final Filter selectionGlow = new Glow();

    public MarbleSprite(MarbleViewModel marbleViewModel, GameScene game) {
	super(CoreImage.load(marbleViewModel.getMarbleType().toString().toLowerCase()
		+ ".png"), 0, 0);
	this.game = game;
	this.viewModel = marbleViewModel;
	this.setAnchor(0.5, 0.5);
	this.setPixelLevelChecks(false);
	Point screenPosition = MarblePositionMapper
		.mapToScreenPosition(marbleViewModel.getPositionOnBoard());
	this.setLocation(screenPosition.x, screenPosition.y);
    }

    @Override
    public void update(int elapsedTime) {
	applyHighlight();
	if (this.isMousePressed()) {
	    MarbleBoardViewModel mbvm = game.clickMarble(viewModel
		    .getPositionOnBoard());
	    game.updateDisplay(mbvm);
	}
	super.update(elapsedTime);
    }

    private void applyHighlight() {
	if (viewModel.isSelected()) {
	    this.setFilter(selectionGlow);
	} else {
	    this.setFilter(null);
	}
    }

    public void setPositionOnBoard(PositionOnBoard positionOnBoard) {
	viewModel.updatePositionOnBoard(positionOnBoard);
    }

    public void setSelected(boolean selected) {
	this.viewModel.setSelected(selected);
    }

}
