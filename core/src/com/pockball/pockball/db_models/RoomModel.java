package com.pockball.pockball.db_models;

import java.util.Random;

public class RoomModel {
    public String roomId;
    public PlayerModel host;
    public PlayerModel client;
    public boolean hostTurn;
    public float timeStamp;

    public RoomModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public RoomModel(String roomId){
        this.roomId = roomId;
    }


    public RoomModel(String roomId, PlayerModel host) {
        this.roomId = roomId;
        this.host = host;
        this.client = null; // this.opponent is set later on
        this.hostTurn = new Random().nextBoolean();
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "roomId='" + roomId + '\'' +
                ", host=" + host +
                ", client=" + client +
                ", hostTurn=" + hostTurn +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
