package com.perhac.games.clickthemarbles.scene;

import pulpcore.Input;
import pulpcore.animation.event.SceneChangeEvent;
import pulpcore.image.Colors;
import pulpcore.image.CoreImage;
import pulpcore.image.filter.DropShadow;
import pulpcore.sound.Sound;
import pulpcore.sprite.Button;
import pulpcore.sprite.Group;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.game.GameConfig;
import com.perhac.games.clickthemarbles.scene.sprite.CloudBuilder;

public class OptionsScene extends TransitioningScene {

    private static final String ENDLESS_GAME_TIP = "Endless game mode, in which your main goal is to pop as many marbles as you can.";
	private static final String NORMAL_GAME_TIP = "Classic game mode, in which you try to clear the board while collecting as many points as possible.";
	private static final int MAX_NUMBER_OF_MARBLE_COLORS = 8;
	private static final String NUM_COLORS_TIP = "Select the number of differnet colors to be used - game difficulty increases with the number chosen.";
    private GameConfig config = GameConfig.getInstance();
    private Button endlessGameButton, normalGameButton;
    private Group buttons;
    private Sound selectedSound, poppedSound;
    private Button backButton;
    private Button spaceBarButton;
    
    private Label gameInfoText;

    @Override
    public void load() {
	poppedSound = Sound.load("popped.wav");
	selectedSound = Sound.load("selected.wav");

	normalGameButton = Button.createLabeledButton("Best Score", 200, 350);
	normalGameButton.setAnchor(0.5, 0.5);
	normalGameButton.setKeyBinding(Input.KEY_1);

	endlessGameButton = Button.createLabeledButton("Best Shot", 600, 350);
	endlessGameButton.setAnchor(0.5, 0.5);
	endlessGameButton.setKeyBinding(Input.KEY_2);
	
	gameInfoText = new Label("", 20, 425);
	gameInfoText.setFilter(new DropShadow(2, 2, Colors.WHITE));
	gameInfoText.setAnchor(0, 0.5);

	ImageSprite background = new ImageSprite("menu-screen.jpg", 0, 0);
	add(background);
	CloudBuilder.addClouds(this);
	ImageSprite gameTitle = new ImageSprite("game-title.png", 0, 0);
	gameTitle.setAnchor(0.5, 0.5);
	gameTitle.setLocation(400, 100);
	add(gameTitle);

	backButton = new Button(CoreImage.load("back-button.png").split(1, 3),
		60, 540);
	backButton.setPixelLevelChecks(false);
	backButton.setAnchor(0.5, 0.5);
	backButton.setKeyBinding(Input.KEY_BACK_SPACE);
	add(backButton);

	spaceBarButton = Button.createLabeledButton("play now", 900, 700);
	spaceBarButton.setKeyBinding(Input.KEY_SPACE);
	add(spaceBarButton);

	add(endlessGameButton);
	add(normalGameButton);

	setupButtons();
	add(buttons);

	add(gameInfoText);


	fadeInFromWhite();
    }

    private void setupButtons() {
	buttons = new Group();
	int preselected = config.getNumberOfColours();
	int w = 0;
	for (int i = 3; i < MAX_NUMBER_OF_MARBLE_COLORS; i++) {
	    Button b = Button
		    .createLabeledToggleButton("" + i, (i - 3) * 60, 0);
	    b.setSelected(i == preselected);
	    b.width.set(50);
	    b.setTag(new Integer(i));
	    buttons.add(b);
	    w += 60;
	}
	w -= 10;
	buttons.width.set(w);
	buttons.height.set(40);
	buttons.setAnchor(0.5, 0);
	buttons.setLocation(400, 250);
    }

    @Override
    public void update(int elapsedTime) {
	if (spaceBarButton.isClicked()) {
	    poppedSound.play();
	    startGame();
	    return;
	}
	if (backButton.isClicked()) {
	    selectedSound.play();
	    fadeOutToBlack();
	    addEvent(new SceneChangeEvent(new MainMenuScene(), 500));
	    return;
	}

	checkForButtonsClicked();

	if (endlessGameButton.isClicked()) {
	    poppedSound.play();
	    config.setEndlessGameMode(true);
	    startGame();
	    return;
	}
	if (normalGameButton.isClicked()) {
	    poppedSound.play();
	    config.setEndlessGameMode(false);
	    startGame();
	    return;
	}
	
	handleGameModeTips();

    }

	private void handleGameModeTips() {
		if (normalGameButton.isMouseHover()){
			gameInfoText.setText(NORMAL_GAME_TIP);
			gameInfoText.alpha.set(255);
		} else {
			if (endlessGameButton.isMouseHover()){
				gameInfoText.setText(ENDLESS_GAME_TIP);
				gameInfoText.alpha.set(255);
			} else {
				if (buttons.isMouseOver()) {
					gameInfoText.setText(NUM_COLORS_TIP);
					gameInfoText.alpha.set(255);
				}else {
					gameInfoText.alpha.set(0);
				}
			}
		}
	}

    private void startGame() {
	int delay = 500;
	fadeOutToWhite();
	addEvent(new SceneChangeEvent(new GameScene(), delay));
    }

    private void checkForButtonsClicked() {
	boolean isAnyButtonClicked = false;
	for (int i = 0; i < buttons.getNumSprites(); i++) {
	    Button b = (Button) buttons.get(i);
	    if (b.isClicked()) {
		isAnyButtonClicked = true;
		break;
	    }
	}
	if (!isAnyButtonClicked) {
	    return;
	}
	for (int i = 0; i < buttons.getNumSprites(); i++) {
	    Button b = (Button) buttons.get(i);
	    boolean select = b.isClicked();
	    b.setSelected(select);
	    if (select) {
		config.setNumberOfColours((Integer) b.getTag());
		selectedSound.play();
	    }
	}
    }
}
