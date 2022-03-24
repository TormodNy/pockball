package com.pockball.pockball.screens.join_game_room;

import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.screens.ScreenController;

import java.util.List;


public class JoinGameController {
    private static JoinGameController joinGameControllerInstance = null;
    private ScreenController screenController;
    private FirebaseController firebaseController;

    private JoinGameController() {
        firebaseController = FirebaseController.getInstance();
    }

    private List<RoomModel> availableRooms;

    public static JoinGameController getInstance() {
        if (joinGameControllerInstance == null) {
            joinGameControllerInstance = new JoinGameController();
        }
        return joinGameControllerInstance;
    }

    public void setAvailableRooms(List<RoomModel> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public List<RoomModel> getAvailableRooms() {
        return availableRooms;
    }

    public void listenForRooms () {
        firebaseController.listenToAvailableRooms();
    }

    public void stopListenForRooms () {
        firebaseController.stopListenToAvailableRooms();
    }

    public void joinGame (String roomId) {
        System.out.println("Joined game with roomID: " + roomId);
    }
}
