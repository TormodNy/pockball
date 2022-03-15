package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.ScreenController;


public class MultiplayerController {
    private static MultiplayerController multiplayerControllerInstance = null;
    private ScreenController screenController;

    private MultiplayerController() {
        // Set multiplayer state
        Context.getInstance().setState(new MultiPlayerState());
    }

    public void resetGame() {
        Context.getInstance().setState(new MultiPlayerState());
    }

    public static MultiplayerController getInstance() {
        if (multiplayerControllerInstance == null) {
            multiplayerControllerInstance = new MultiplayerController();
        }
        return multiplayerControllerInstance;
    }

    public void checkGameOver() {

    }
}
