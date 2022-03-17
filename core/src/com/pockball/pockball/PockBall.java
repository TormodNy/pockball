package com.pockball.pockball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class PockBall extends Game {

	public static final int WIDTH = 25;
	public static final int HEIGHT = 15;
	public static final String TITLE = "PockBall";

	private ScreenController screenController;
	public static Camera camera;

	public PockBall() {
		this.screenController = ScreenController.getInstance();
	}

	// Have to set this to main menu when it's implemented
	@Override
	public void create () {
		this.screenController.changeScreen(ScreenModel.Screen.MULTIPLAYER);

		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
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
