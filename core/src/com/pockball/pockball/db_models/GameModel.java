package com.pockball.pockball.db_models;

import java.util.List;

public class GameModel {
    public String gameId;
    public List<String> players;

    public GameModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public GameModel(String gameId, List<String> players){
        this.gameId = gameId;
        this.players = players;
    }


}
