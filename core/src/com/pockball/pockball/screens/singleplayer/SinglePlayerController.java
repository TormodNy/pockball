package com.pockball.pockball.screens.singleplayer;

import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.screens.GameController;


public class SinglePlayerController extends GameController {
    public static SinglePlayerController singlePlayerControllerInstance;

    private SinglePlayerController() {
    }

    public static SinglePlayerController getInstance() {
        if (singlePlayerControllerInstance == null) {
            singlePlayerControllerInstance = new SinglePlayerController();
        }

        currentController = singlePlayerControllerInstance;
        return singlePlayerControllerInstance;
    }

    public int getNumberOfShots() {
        return GameModeContext.getInstance().getState().getNumberOfShots();
    }

    public void reset() {
        GameModeContext.getInstance().getState().reset();
    }

    @Override
    public String getCurrentStateString() {
        return "Single player";
    }
}
