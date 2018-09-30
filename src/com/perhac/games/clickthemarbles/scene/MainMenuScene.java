package com.perhac.games.clickthemarbles.scene;

import pulpcore.Input;
import pulpcore.animation.event.SceneChangeEvent;
import pulpcore.image.Colors;
import pulpcore.image.CoreImage;
import pulpcore.image.filter.DropShadow;
import pulpcore.image.filter.Filter;
import pulpcore.image.filter.FilterChain;
import pulpcore.image.filter.Stroke;
import pulpcore.sound.Sound;
import pulpcore.sprite.Button;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.scene.sprite.CloudBuilder;

public class MainMenuScene extends TransitioningScene {

    Button playButton;
    private Sound poppedSound;

    @Override
    public void load() {

	poppedSound = Sound.load("popped.wav");

	playButton = new Button(CoreImage.load("play-button.png").split(1, 3),
		400, 300);
	playButton.setAnchor(0.5, 0.5);
	playButton.setKeyBinding(Input.KEY_SPACE);

	add(new ImageSprite("menu-screen.jpg", 0, 0));
	CloudBuilder.addClouds(this); 
	ImageSprite gameTitle = new ImageSprite("game-title.png", 0, 0);
	gameTitle.setAnchor(0.5, 0.5);
	gameTitle.setLocation(400, 100);
	add(gameTitle);
	add(playButton);

	Label label = new Label(
		"Java remake of SameGame. http://en.wikipedia.org/wiki/SameGame",
		40, 530);
	FilterChain fg = new FilterChain(new Filter[] {
		new Stroke(Colors.WHITE, 1),
		new DropShadow(3, 3, Colors.rgba(0, 0, 0, 96), 2) });
	label.setFilter(fg);
	add(label);

	fadeInFromBlack();
    }

    @Override
    public void update(int elapsedTime) {
	if (playButton.isClicked()) {
	    poppedSound.play();
	    fadeOutToWhite();
	    addEvent(new SceneChangeEvent(new OptionsScene(), 500));
	}
    }

}
