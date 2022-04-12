package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.game_states.State;
import com.pockball.pockball.screens.GameController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;


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

    public String getMyBallType () {
        MultiPlayerState state = (MultiPlayerState) Context.getInstance().getState();

        if (state.getMyBallType() != null) {
            return state.getMyBallType().toString();
        }

        return "Type";
    }

    public void removeRoom () {
        MultiPlayerState state = (MultiPlayerState) Context.getInstance().getState();
        state.removeRoom();
    }

    @Override
    public String getCurrentStateString() {
        State state = Context.getInstance().getState();
        if (!state.getIdle()) return "Waiting for balls to stop.";
        if (state.canPerformAction()) return "Your turn. Make a shot!";
        if (!state.getIsMyTurn()) return "Opponents turn";

        return "Unhandled state";
    }
}
