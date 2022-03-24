package com.pockball.pockball.screens.create_game_room;

import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class CreateGameRoomController {
    private static CreateGameRoomController createGameRoomControllerInstance = null;
    private ScreenController screenController;
    private FirebaseController firebaseController;
    private RoomModel roomModel;

    private CreateGameRoomController() {
        firebaseController = FirebaseController.getInstance();
    }

    public static CreateGameRoomController getInstance() {
        if (createGameRoomControllerInstance == null) {
            createGameRoomControllerInstance = new CreateGameRoomController();
        }
        return createGameRoomControllerInstance;
    }

    protected void testDb() {
        createRoom();
        firebaseController.listenToClientsInGame(roomModel.roomId);
        joinRoom();
    }

    public void createRoom() {
        PlayerModel host = new PlayerModel("player1");

        roomModel = new RoomModel("game2", host);
        firebaseController.writeToDb(roomModel.roomId, roomModel);
    }

    public void joinRoom() {
        PlayerModel opponent = new PlayerModel("client1");
        firebaseController.writeToDb(roomModel.roomId + ".client", opponent);
    }

    public void notifyNewClient(PlayerModel client) {
        if (client == null) return;

        System.out.println("notifyNewOpponent() -> " + client);
        firebaseController.stopListenToClientsInGame();
        roomModel.client = client;
        startGame();
    }

    public void startGame() {
        System.out.println("startGame() -> " + roomModel.roomId);
        Context.getInstance().setState(new MultiPlayerState(roomModel, true));
        ScreenController.getInstance().changeScreen(ScreenModel.Screen.MULTIPLAYER, ScreenModel.Screen.CREATE_GAME);
    }
}
