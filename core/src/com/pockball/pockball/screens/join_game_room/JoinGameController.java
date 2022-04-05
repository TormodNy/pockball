package com.pockball.pockball.screens.join_game_room;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

import java.util.List;


public class JoinGameController {
    private static JoinGameController joinGameControllerInstance = null;
    private final FirebaseController firebaseController;

    private JoinGameController() {
        firebaseController = FirebaseController.getInstance();
    }

    private String errorMessage = "";

    public static JoinGameController getInstance() {
        if (joinGameControllerInstance == null) {
            joinGameControllerInstance = new JoinGameController();
        }
        return joinGameControllerInstance;
    }

    public void getRoom(String roomId) {
        firebaseController.getRoom(roomId);
    }

    public String getErrorMessage () {
        return errorMessage;
    }

    public void joinGame (RoomModel roomModel) {
        if (roomModel == null || roomModel.client != null) {
            errorMessage = "Invalid room";
            return;
        }

        // Join room in db
        Context.getInstance().setState(new MultiPlayerState(roomModel, false));
        ScreenController.getInstance().changeScreen(
                ScreenModel.Screen.MULTIPLAYER,
                ScreenModel.Screen.JOIN_GAME
        );
    }
}

