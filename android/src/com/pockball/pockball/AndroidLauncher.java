package com.pockball.pockball;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.firebase.FirebaseService;

public class AndroidLauncher extends AndroidApplication {
	PockBall game;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		this.game = new PockBall(new FirebaseService());
		initialize(game, config);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		game.dispose();
	}
}
