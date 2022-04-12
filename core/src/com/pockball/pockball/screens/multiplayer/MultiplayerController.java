package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.State;
import com.pockball.pockball.screens.GameController;
import com.pockball.pockball.screens.ScreenController;


public class MultiplayerController extends GameController {
    private static MultiplayerController multiplayerControllerInstance = null;

    private MultiplayerController() {
    }

    public static MultiplayerController getInstance() {
        if (multiplayerControllerInstance == null) {
            multiplayerControllerInstance = new MultiplayerController();
        }

        currentController = multiplayerControllerInstance;
        return multiplayerControllerInstance;
    }

    @Override
    public String getCurrentStateString() {
        State state = Context.getInstance().getState();
        if (state.canPerformAction()) return "Your turn. Make a shot!";
        else if (!state.getIsMyTurn()) return "Opponents turn";
        else if (!state.getIdle()) return "Waiting for balls to stop.";

        return "Unhandled state";
    }
}
