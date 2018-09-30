package com.perhac.games.clickthemarbles.game;

import java.util.Arrays;
import java.util.Random;

import com.perhac.games.clickthemarbles.model.Marble;
import com.perhac.games.clickthemarbles.model.MarbleType;

public class MarbleGenerator {

    private static final Random random = new Random();
    private final MarbleType[] availableMarbleTypes;
    private final int numberOfColours;

    public MarbleGenerator(int numberOfColours) {
	this.numberOfColours = numberOfColours;
	availableMarbleTypes = Arrays.copyOfRange(MarbleType.values(), 0,
		numberOfColours);
    }

    public Marble next() {
	MarbleType nextMarbleType = availableMarbleTypes[random
		.nextInt(numberOfColours)];
	return new Marble(nextMarbleType);
    }

}
