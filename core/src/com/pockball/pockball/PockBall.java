package com.pockball.pockball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.firebase.FirebaseInterface;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class PockBall extends Game {

	public static final int WIDTH = 25;
	public static final int HEIGHT = 15;
	public static final String TITLE = "PockBall";

	public static Camera camera;

	private final ScreenController screenController;
	private final FirebaseController firebaseController;

	public PockBall(FirebaseInterface firebaseInterface) {
		this.screenController = ScreenController.getInstance();
		this.firebaseController = FirebaseController.getInstance();
		firebaseController.setFirebaseInterface(firebaseInterface);
	}

	@Override
	public void create () {
		this.screenController.changeScreen(ScreenModel.Screen.MAINMENU, null);

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
