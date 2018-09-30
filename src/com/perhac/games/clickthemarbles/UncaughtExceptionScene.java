package com.perhac.games.clickthemarbles;

import pulpcore.Build;
import pulpcore.CoreSystem;
import pulpcore.Stage;
import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.platform.ConsoleScene;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.Button;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;

import com.perhac.games.clickthemarbles.scene.LoadingScene;

public class UncaughtExceptionScene extends Scene2D {

    // Only send once per browser session to avoid talkback spam
    static boolean uploadedThisSession = false;

    Button retryButton;
    Button consoleButton;

    @Override
    public void load() {
	add(new FilledSprite(Colors.BLACK));

	// Send the talkback fields via POST
	if (!uploadedThisSession
		&& "true".equals(CoreSystem.getAppProperty("talkback"))) {
	    uploadedThisSession = true;
	    CoreSystem.uploadTalkBackFields("/talkback.py");
	}

	CoreFont font = CoreFont.getSystemFont().tint(Colors.WHITE);
	Group message = Label
		.createMultilineLabel(
			font,
			"Ooops! We're terribly sorry but the application encountered some problems that it is not able to recover from.",
			Stage.getWidth() / 2, 150, Stage.getWidth() - 20);
	message.setAnchor(0.5, 0.5);
	add(message);

	if (Build.DEBUG) {
	    consoleButton = Button.createLabeledButton("Show Console", Stage
		    .getWidth() / 2, 300);
	    consoleButton.setAnchor(0.5, 0.5);
	    add(consoleButton);
	}

	retryButton = Button.createLabeledButton("Restart",
		Stage.getWidth() / 2, 350);
	retryButton.setAnchor(0.5, 0.5);
	add(retryButton);
    }

    @Override
    public void update(int elapsedTime) {
	if (retryButton.isClicked()) {
	    Stage.setScene(new LoadingScene());
	}
	if (Build.DEBUG && consoleButton.isClicked()) {
	    Stage.pushScene(new ConsoleScene());
	}
    }
}
