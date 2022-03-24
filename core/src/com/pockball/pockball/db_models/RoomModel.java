package com.pockball.pockball.db_models;

import java.util.ArrayList;
import java.util.List;

public class RoomModel {
    public String roomId;
    public PlayerModel host;
    public PlayerModel opponent;
    public boolean hostTurn;
    public float timeStamp;
    public List<ShotModel> shots;

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
        this.opponent = null; // this.opponent is set later on
        this.hostTurn = true; // Todo - change to random bool
        this.shots = new ArrayList<>();
    }

    public void addShot(ShotModel shot) {
        this.shots.add(shot);
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
                },
                hostTurn: boolean,
                timeStamp: int, // Last hit
                winner: null | playerID,
                        shots: [
                {
                    force: Vector2, // Direction with force
                }
                    ],

            },
            room2: { ... }
        }

    }
    */
}
