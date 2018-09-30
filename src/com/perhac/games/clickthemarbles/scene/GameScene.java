package com.perhac.games.clickthemarbles.scene;

import java.awt.Point;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import pulpcore.CoreSystem;
import pulpcore.Input;
import pulpcore.animation.Easing;
import pulpcore.animation.event.RemoveSpriteEvent;
import pulpcore.animation.event.SceneChangeEvent;
import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.image.CoreImage;
import pulpcore.scene.Scene2D;
import pulpcore.sound.Sound;
import pulpcore.sprite.Button;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.ImageSprite;

import com.perhac.games.clickthemarbles.game.*;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleBoardViewModel;
import com.perhac.games.clickthemarbles.game.viewmodel.MarbleViewModel;
import com.perhac.games.clickthemarbles.model.GameMode;
import com.perhac.games.clickthemarbles.model.PositionOnBoard;
import com.perhac.games.clickthemarbles.scene.sprite.FloatingPointsDisplay;
import com.perhac.games.clickthemarbles.scene.sprite.MarbleSprite;

public class GameScene extends Scene2D {

    private final CoreFont font = CoreFont.load("game.font.png");
    private Game game;
    private Group board;
    private Button undoButton, muteButton, exitButton, tapTwiceButton;
    private Dictionary<Integer, MarbleSprite> marbles = new Hashtable<Integer, MarbleSprite>();
    private Sound poppedSound, selectedSound, gameOverSound;
    private FloatingPointsDisplay floatingPointsDisplay;
	private final GameConfig config = GameConfig.getInstance();
	private MarbleBoardViewModel currentBoardVM;

    @Override
    public void load() {
	game = new Game();

	add(new ImageSprite("game-bg.jpg", 0, 0));

	board = new Group();
	add(board);

	muteButton = new Button(CoreImage.load("mute-button.png").split(3, 2),
		640, 20, true);
	muteButton.setAnchor(1.0, 0.0);
	add(muteButton);

	exitButton = new Button(CoreImage.load("exit-button.png").split(1, 3),
		770, 20);
	exitButton.setAnchor(1.0, 0.0);
	exitButton.setKeyBinding(Input.KEY_ESCAPE);
	add(exitButton);

	undoButton = new Button(CoreImage.load("back-button.png").split(1, 3),
		60, 60);
	undoButton.setAnchor(0.5, 0.5);
	undoButton.setKeyBinding(Input.KEY_BACK_SPACE);
	add(undoButton);
	
	GameMode currentGameMode = config.getGameMode();
	tapTwiceButton = Button.createLabeledToggleButton("First Click Selects", 400, 200);
	tapTwiceButton.setLocation(600, 65);
	tapTwiceButton.width.set(tapTwiceButton.width.get()+10);
	tapTwiceButton.setSelected(config.isSelectFirst());
	add(tapTwiceButton);

	//must have a game before asking for Score object
	MarbleBoardViewModel boardVM = currentBoardVM = game.newGame(); 
	add(HUDBuilder.buildHUD(currentGameMode, font, game.getScore(),
		game.getStopWatch()));

	floatingPointsDisplay = new FloatingPointsDisplay();
	floatingPointsDisplay.setLocation(400, 300);
	add(floatingPointsDisplay);

	poppedSound = Sound.load("popped.wav");
	selectedSound = Sound.load("selected.wav");
	gameOverSound = Sound.load("gameOver.wav");
	
	updateDisplay(boardVM);

	FilledSprite whiteness = new FilledSprite(Colors.WHITE);
	whiteness.alpha.set(255);
	add(whiteness);
	whiteness.alpha.animateTo(0, 600);
    }

    public void updateDisplay(MarbleBoardViewModel boardVM) {
    	this.currentBoardVM = boardVM;
	boolean skipAnimation = boardVM.skipAnimation;
	Enumeration<Integer> keys = marbles.keys();
	MarbleSprite lastSelectedMarble = null;
	int selectedMarblesCount = boardVM.selectedMarbles.size();

	if (selectedMarblesCount > 0) {
	    while (keys.hasMoreElements()) {
		Integer i = keys.nextElement();
		boolean isSelected = boardVM.selectedMarbles.contains(i);
		MarbleSprite marbleSprite = marbles.get(i);
		marbleSprite.setSelected(isSelected);
		if (isSelected) {
		    lastSelectedMarble = marbleSprite;
		}
	    }
	}

	if (selectedMarblesCount > 0) {
	    selectedSound.play();
	    showSelectionScore(selectedMarblesCount,
		    lastSelectedMarble.x.getAsInt(),
		    lastSelectedMarble.y.getAsInt());
	} else {
	    hideSelectionScore(selectedMarblesCount);
	}

	for (MarbleViewModel mvm : boardVM.toRemove) {
	    MarbleSprite marbleToRemove = marbles.remove(mvm.getMarbleId());
	    if (!skipAnimation) {
		marbleToRemove.enabled.set(false);
		marbleToRemove.scaleTo(0, 0, 300, Easing.BACK_IN);
		addEvent(new RemoveSpriteEvent(board, marbleToRemove, 300));
	    } else {
		board.remove(marbleToRemove);
	    }
	}
	if (boardVM.toRemove.size() > 0) {
	    poppedSound.play();
	}

	for (MarbleViewModel mvm : boardVM.toMove) {
	    MarbleSprite marbleToMove = marbles.get(mvm.getMarbleId());
	    Point p = MarblePositionMapper.mapToScreenPosition(mvm
		    .getPositionOnBoard());
	    marbleToMove.setPositionOnBoard(mvm.getPositionOnBoard());
	    marbleToMove.x.animateTo(p.x, skipAnimation ? 0 : 300,
		    Easing.REGULAR_IN_OUT, skipAnimation ? 0 : 300);
	    marbleToMove.y.animateTo(p.y, skipAnimation ? 0 : 300,
		    Easing.REGULAR_IN_OUT, skipAnimation ? 0 : 300);
	}

	for (MarbleViewModel mvm : boardVM.toAdd) {
	    MarbleSprite marble = new MarbleSprite(mvm, this);
	    marble.alpha.set(0);
	    board.add(marble);
	    marbles.put(mvm.getMarbleId(), marble);
	    marble.alpha.animateTo(255, skipAnimation ? 0 : 500, Easing.NONE,
		    skipAnimation ? 0 : 600);
	}

	if (!boardVM.playable) {
	    game.stopTheWatch();
	    gameOverSound.play();
	    board.alpha.animateTo(127, 500);
	} else {
	    if (board.alpha.get() < 255) {
		board.alpha.animateTo(255, 300);
	    }
	}
    }

    public MarbleBoardViewModel clickMarble(PositionOnBoard positionOnBoard) {
	return game.marbleClicked(positionOnBoard);
    }

    private void showSelectionScore(int selectedMarbles, int x, int y) {
	int pointsSelected = PointsCalculator.calculatePoints(selectedMarbles);
	floatingPointsDisplay.setPointsToDisplay(pointsSelected);
	int duration = 150;
	floatingPointsDisplay.x.animateTo(x, duration);
	floatingPointsDisplay.y.animateTo(y, duration);
	if (floatingPointsDisplay.alpha.get() < 255) {
	    floatingPointsDisplay.alpha.animateTo(255, duration);
	}
    }

    private void hideSelectionScore(int selectedMarbles) {
	floatingPointsDisplay.alpha.animateTo(0, 150);
    }

    @Override
    public void update(int elapsedTime) {
	if (undoButton.isClicked()) {
	    selectedSound.play();
	    updateDisplay(game.undo());	    
	}

	if (exitButton.isClicked()) {
	    selectedSound.play();
	    FilledSprite blackness = new FilledSprite(Colors.BLACK);
	    blackness.alpha.set(0);
	    add(blackness);
	    blackness.alpha.animateTo(255, 600);
	    addEvent(new SceneChangeEvent(new MainMenuScene(), 600));
	}

	if (muteButton.isClicked()) {
	    CoreSystem.setMute(muteButton.isSelected());
	    selectedSound.play();
	}

	if (tapTwiceButton.isClicked()) {
		deselectAll();
	    config.setSelectFirst(tapTwiceButton.isSelected());
	    selectedSound.play();
	}

	super.update(elapsedTime);
    }

	private void deselectAll() {
		Enumeration<MarbleSprite> elements = marbles.elements();
		while (elements.hasMoreElements()){
			MarbleSprite marbleSprite = elements.nextElement();
			marbleSprite.setSelected(false);
		}
		floatingPointsDisplay.alpha.set(0);
		currentBoardVM.selectedMarbles.clear();
		game.getController().reset();
	}

}
