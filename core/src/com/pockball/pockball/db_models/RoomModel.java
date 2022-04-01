package com.pockball.pockball.db_models;

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
        this.hostTurn = true; // Todo - change to random bool
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

    /*{
        rooms: {
            room1312: {
                host: {
                    playerID,
                            ballType,
                            score: ballID[] // Balls fallen into hole
                },
                opponent: null | {
                        playerID,
                        ballType,
                        score: ballID[] // Balls fallen into hole
                        shots: [
                {
                    force: Vector2, // Direction with force
                }
                    ],
                },
                hostTurn: boolean,
                timeStamp: int, // Last hit
                winner: null | playerID,

            },
            room2: { ... }
        }

    }
    */
}
