package com.perhac.games.clickthemarbles.game;

import java.util.Properties;

import pulpcore.Assets;

import com.perhac.games.clickthemarbles.model.GameMode;

public class GameConfig {

    public final int NUMBER_OF_ROWS = 15;
    public final int NUMBER_OF_COLUMNS = 25;

    private static GameConfig instance = null;

    private int numberOfColours = 4;
    private GameMode gameMode = GameMode.CLICK_ALL;
    private boolean selectFirst = true;

    private GameConfig() {
	Properties gameConfig = new Properties();
	try {
	    gameConfig.load(Assets.getAsStream("gameConfig.properties"));
	} catch (Exception e) {
	    System.out.println("Failed to load configuration file!");
	}

	loadAttributesFromProperties(gameConfig);
    }

    private void loadAttributesFromProperties(Properties properties) {
	numberOfColours = Integer.parseInt(properties.getProperty(
		"numberOfColours", String.valueOf(numberOfColours)));
	gameMode = GameMode.valueOf(properties.getProperty("gameMode", gameMode
		.toString()));
	selectFirst = Boolean.parseBoolean(properties.getProperty(
		"selectFirst", String.valueOf(selectFirst)));
    }

    public int getNumberOfColours() {
	return numberOfColours;
    }

    public GameMode getGameMode() {
	return gameMode;
    }

    public boolean isSelectFirst() {
	return selectFirst;
    }

    public static GameConfig getInstance() {
	if (instance == null) {
	    instance = new GameConfig();
	}
	return instance;
    }

    public void setSelectFirst(boolean selected) {
	this.selectFirst = selected;
    }

    public void setEndlessGameMode(boolean endless) {
	this.gameMode = endless ? GameMode.ENDLESS : GameMode.CLICK_ALL;
    }

    public void setNumberOfColours(int numberOfColours) {
	this.numberOfColours = numberOfColours;
    }

}
