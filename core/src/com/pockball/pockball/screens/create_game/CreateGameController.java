package com.pockball.pockball.screens.create_game;

import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.screens.ScreenController;

import java.util.ArrayList;


public class CreateGameController {
    private static CreateGameController createGameControllerInstance = null;
    private ScreenController screenController;
    private FirebaseController firebaseController;

    private CreateGameController() {
        firebaseController = FirebaseController.getInstance();
        testDb();
    }

    public static CreateGameController getInstance() {
        if (createGameControllerInstance == null) {
            createGameControllerInstance = new CreateGameController();
        }
        return createGameControllerInstance;
    }

    private void testDb() {
        createRoom();
        firebaseController.listenToRoomChanges("newRoom");
    }

    public void createRoom() {
        ArrayList<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");

        //GameModel gameModel = new GameModel("gameId1", players);
        //firebaseController.writeToDb("newRoom", gameModel);
    }

    public void fireChangeInRoom(RoomModel roomModel) {
        //System.out.println("fireChangeInRoom() -> " + gameModel.gameId);
    }
}
