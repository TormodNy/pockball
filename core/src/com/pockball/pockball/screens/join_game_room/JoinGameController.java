package com.pockball.pockball.screens.join_game_room;

import com.pockball.pockball.screens.ScreenController;


public class JoinGameController {
    private static JoinGameController joinGameControllerInstance = null;
    private ScreenController screenController;

    private JoinGameController() {
    }

    public static JoinGameController getInstance() {
        if (joinGameControllerInstance == null) {
            joinGameControllerInstance = new JoinGameController();
        }
        return joinGameControllerInstance;
    }
}
