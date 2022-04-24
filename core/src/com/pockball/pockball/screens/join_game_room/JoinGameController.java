package com.pockball.pockball.screens.join_game_room;

import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.game_modes.MultiPlayerGameMode;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;


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
        GameModeContext.getInstance().setState(new MultiPlayerGameMode(roomModel, false));
        ScreenController.getInstance().changeScreen(
                ScreenModel.Screen.MULTIPLAYER,
                ScreenModel.Screen.JOIN_GAME
        );
    }
}

