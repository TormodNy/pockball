package com.pockball.pockball.screens.singleplayer;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.SinglePlayerState;
import com.pockball.pockball.screens.GameController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;


public class SinglePlayerController extends GameController {
    public static SinglePlayerController singlePlayerControllerInstance;
    private ScreenController screenController;

    private SinglePlayerController() {
        // Set single player state
        Context.getInstance().setState(new SinglePlayerState());
    }

    public static SinglePlayerController getInstance() {
        if (singlePlayerControllerInstance == null) {
            singlePlayerControllerInstance = new SinglePlayerController();
        }

        currentController = singlePlayerControllerInstance;
        return singlePlayerControllerInstance;
    }

    public void checkGameOver() {
        screenController.changeScreen(ScreenModel.Screen.GAMEOVER, ScreenModel.Screen.SINGLEPLAYER);
    }

    public int getNumberOfShots() {
        return Context.getInstance().getState().getNumberOfShots();
    }

    public void reset() {
        Context.getInstance().getState().reset();
    }

    @Override
    public String getCurrentStateString() {
        return "Single player";
    }
}
