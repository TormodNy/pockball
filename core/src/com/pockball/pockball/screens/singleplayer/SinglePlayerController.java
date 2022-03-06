package com.pockball.pockball.screens.singleplayer;

import com.pockball.pockball.screens.ScreenController;

public class SinglePlayerController {
    private static SinglePlayerController singlePlayerControllerInstance = null;
    private ScreenController screenController;

    private SinglePlayerController() {

    }

    public static SinglePlayerController getInstance() {
        if (singlePlayerControllerInstance == null) {
            singlePlayerControllerInstance = new SinglePlayerController();
        }
        return singlePlayerControllerInstance;
    }

    public void checkGameOver() {

    }
}
