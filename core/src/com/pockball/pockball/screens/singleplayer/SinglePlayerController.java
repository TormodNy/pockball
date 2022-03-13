package com.pockball.pockball.screens.singleplayer;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.SinglePlayerState;
import com.pockball.pockball.screens.ScreenController;


public class SinglePlayerController {
    private static SinglePlayerController singlePlayerControllerInstance = null;
    private ScreenController screenController;

    private SinglePlayerController() {
        // Add player instances
        Entity player = EntityFactory.getInstance().createPlayer("singlePlayerPlayer");
        Engine.getInstance().addEntity(EntityFactory.getInstance().createPlayer("singlePlayerPlayer"));

        // Set single player state
        Context.getInstance().setState(new SinglePlayerState());
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
