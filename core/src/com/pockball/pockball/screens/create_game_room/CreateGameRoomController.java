package com.pockball.pockball.screens.create_game_room;

import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.game_modes.MultiPlayerGameMode;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

import java.util.Random;

public class CreateGameRoomController {
    private static CreateGameRoomController createGameRoomControllerInstance = null;
    private final ScreenController screenController;
    private final FirebaseController firebaseController;

    private RoomModel roomModel;
    private String randomRoomId;
    private String confirmedRoomId = "";

    private CreateGameRoomController() {
        screenController = ScreenController.getInstance();
        firebaseController = FirebaseController.getInstance();
    }

    public static CreateGameRoomController getInstance() {
        if (createGameRoomControllerInstance == null) {
            createGameRoomControllerInstance = new CreateGameRoomController();
        }
        return createGameRoomControllerInstance;
    }

    public void createRoom() {
        Random random = new Random();
        randomRoomId = "" + (random.nextInt(9000) + 1000);

        firebaseController.checkRoomId(randomRoomId);
    }

    public void idIsFree (boolean isFree) {
        if (isFree) {
            PlayerModel host = new PlayerModel("player1");

            confirmedRoomId = randomRoomId;
            roomModel = new RoomModel(confirmedRoomId, host);
            firebaseController.writeToDb(roomModel.roomId, roomModel);
            firebaseController.listenToClientsInGame(roomModel.roomId);
        } else {
            createRoom();
        }
    }

    public String getConfirmedRoomId () {
        return confirmedRoomId;
    }

    public void removeRoom() {
        firebaseController.removeFromDb(roomModel.roomId);
        firebaseController.stopListenToClientsInGame();
    }

    public void notifyNewClient(PlayerModel client) {
        if (client == null) return;

        firebaseController.stopListenToClientsInGame();
        roomModel.client = client;
        startGame();
    }

    public void startGame() {
        GameModeContext.getInstance().setState(new MultiPlayerGameMode(roomModel, true));
        screenController.changeScreen(ScreenModel.Screen.MULTIPLAYER, ScreenModel.Screen.CREATE_GAME);
    }
}
