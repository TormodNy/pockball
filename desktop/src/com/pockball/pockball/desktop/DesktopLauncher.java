package com.pockball.pockball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.desktop.firebase.FirebaseController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PockBall.WIDTH * 32;
		config.height = PockBall.HEIGHT * 32;
		config.title = PockBall.TITLE;
		new LwjglApplication(new PockBall(new FirebaseController()), config);
	}
}
