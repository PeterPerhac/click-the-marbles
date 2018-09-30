package com.perhac.games.clickthemarbles.scene;

import pulpcore.Build;
import pulpcore.CoreSystem;
import pulpcore.Stage;

import com.perhac.games.clickthemarbles.ProjectBuild;
import com.perhac.games.clickthemarbles.UncaughtExceptionScene;

public class LoadingScene extends pulpcore.scene.LoadingScene {

    public LoadingScene() {
	super("ClickTheMarbles-" + ProjectBuild.VERSION + ".zip",
		new MainMenuScene());

	CoreSystem.setTalkBackField("app.name", "ClickTheMarbles");
	CoreSystem.setTalkBackField("app.version", ProjectBuild.VERSION);

	Stage.setUncaughtExceptionScene(new UncaughtExceptionScene());
	Stage.invokeOnShutdown(new Runnable() {
	    public void run() {
		// Shutdown network connections, DB connections, etc.
	    }
	});
    }

    @Override
    public void load() {

	// Deter hotlinking
	String[] validHosts = { "perhac.com", "www.perhac.com", };
	if (!Build.DEBUG && !CoreSystem.isValidHost(validHosts)) {
	    CoreSystem.showDocument("http://www.perhac.com/");
	} else {
	    // Start loading the zip
	    super.load();
	}
    }

    @Override
    public void update(int elapsedTime) {
	super.update(elapsedTime);
	// TODO add code to have some loading progress indicator
    }
}
