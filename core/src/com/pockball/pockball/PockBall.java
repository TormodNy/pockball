package com.pockball.pockball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class PockBall extends Game {
	private ScreenController screenController;

	public PockBall() {
		this.screenController = ScreenController.getInstance();
	}

	// Have to set this to main menu when it's implemented
	@Override
	public void create () {
		this.screenController.changeScreen(ScreenModel.Screen.SINGLEPLAYER);
	}

	@Override
	public void render () {
		screenController.getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		if (screenController != null) {
			screenController.dispose();
		}
	}
}
