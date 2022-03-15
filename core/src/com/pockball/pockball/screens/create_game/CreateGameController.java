package com.pockball.pockball.screens.create_game;

import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.ScreenController;


public class CreateGameController {
    private static CreateGameController createGameControllerInstance = null;
    private ScreenController screenController;
    private FirebaseController firebaseController;

    private CreateGameController() {
        System.out.println("MultiplayerController");
        this.firebaseController = FirebaseController.getInstance();
        testDb();
    }

    private void testDb() {
        firebaseController.writeToDb("game1", "testVal2");
    }

    public static CreateGameController getInstance() {
        if (createGameControllerInstance == null) {
            createGameControllerInstance = new CreateGameController();
        }
        return createGameControllerInstance;
    }

    public void checkGameOver() {

    }
}
