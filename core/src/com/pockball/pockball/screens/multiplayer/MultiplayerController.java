package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.GameController;
import com.pockball.pockball.screens.ScreenController;


public class MultiplayerController extends GameController {
    private static MultiplayerController multiplayerControllerInstance = null;
    private ScreenController screenController;

    private MultiplayerController() {
    }


    public void resetGame() {
    }

    public static MultiplayerController getInstance() {
        if (multiplayerControllerInstance == null) {
            multiplayerControllerInstance = new MultiplayerController();
        }

        currentController = multiplayerControllerInstance;
        return multiplayerControllerInstance;
    }

    public void checkGameOver() {

    }
}
