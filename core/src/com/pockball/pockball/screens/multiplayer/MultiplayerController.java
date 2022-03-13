package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.game_states.SinglePlayerState;
import com.pockball.pockball.screens.ScreenController;


public class MultiplayerController {
    private static MultiplayerController singlePlayerControllerInstance = null;
    private ScreenController screenController;

    private MultiplayerController() {
        // Set multiplayer state
        Context.getInstance().setState(new MultiPlayerState());
    }

    public static MultiplayerController getInstance() {
        if (singlePlayerControllerInstance == null) {
            singlePlayerControllerInstance = new MultiplayerController();
        }
        return singlePlayerControllerInstance;
    }

    public void checkGameOver() {

    }
}
