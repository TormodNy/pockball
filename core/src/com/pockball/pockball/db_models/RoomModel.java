package com.pockball.pockball.db_models;

import java.util.List;

public class RoomModel {
    public String roomId;

    public RoomModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public RoomModel(String roomId){
        this.roomId = roomId;
    }


}
