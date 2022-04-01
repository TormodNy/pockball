package com.pockball.pockball.screens.create_game_room;

import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.MultiPlayerState;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

import java.util.Random;

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

    public String createRoom() {
        PlayerModel host = new PlayerModel("player1");

        Random random = new Random();
        String roomId = "" + (random.nextInt(9000) + 1000);

        roomModel = new RoomModel(roomId, host);
        firebaseController.writeToDb(roomModel.roomId, roomModel);
        firebaseController.listenToClientsInGame(roomModel.roomId);

        return roomId;
    }

    public void removeRoom() {
        firebaseController.removeFromDb(roomModel.roomId);
        firebaseController.stopListenToClientsInGame();
    }

    public void notifyNewClient(PlayerModel client) {
        if (client == null) return;

        firebaseController.stopListenToClientsInGame();
        System.out.println("notifyNewClient " + client);
        roomModel.client = client;
        startGame();
    }

    public void startGame() {
        System.out.println("startGame " + roomModel.roomId);
        Context.getInstance().setState(new MultiPlayerState(roomModel, true));
        ScreenController.getInstance().changeScreen(ScreenModel.Screen.MULTIPLAYER, ScreenModel.Screen.CREATE_GAME);
    }
}
