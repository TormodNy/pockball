package com.pockball.pockball.screens.create_game_room;

import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.screens.ScreenController;

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
        firebaseController.listenToOpponentInGame(roomModel.roomId);
        joinRoom();
    }

    public void createRoom() {
        PlayerModel host = new PlayerModel("player1");

        roomModel = new RoomModel("game2", host);
        firebaseController.writeToDb(roomModel.roomId, roomModel);
    }

    public void joinRoom() {
        PlayerModel opponent = new PlayerModel("opponent1");
        firebaseController.writeToDb( roomModel.roomId + ".opponent", opponent);
    }

    public void notifyNewOpponent(PlayerModel opponent) {
        if (opponent == null) return;
        System.out.println("notifyNewOpponent() -> " + opponent);

        firebaseController.stopListenToOpponentInGame();

        startGame();
    }

    public void startGame() {
        System.out.println("startGame() -> " + roomModel.roomId);

        
    }
}
