package com.perhac.games.clickthemarbles.game;

import static org.junit.Assert.assertEquals;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Test;

import com.perhac.games.clickthemarbles.model.Marble;
import com.perhac.games.clickthemarbles.model.MarbleType;

public class MarbleGeneratorTest {

    @Test
    public void AnyMarbleGenerator_GeneratesAppropriateNumberOfMarbleTypes() {
	for (int distinctTypes = 1; distinctTypes < MarbleType.values().length; distinctTypes++) {
	    // Arrange
	    MarbleGenerator generator = new MarbleGenerator(distinctTypes);
	    Dictionary<MarbleType, Object> typesGenerated = new Hashtable<MarbleType, Object>();

	    // Act
	    for (int i = 0; i < 1000; i++) {
		Marble marble = generator.next();
		typesGenerated.put(marble.getType(), "something");
	    }

	    // Assert
	    assertEquals(distinctTypes, typesGenerated.size());
	}
    }

}
