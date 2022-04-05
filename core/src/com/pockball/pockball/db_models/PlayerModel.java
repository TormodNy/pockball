package com.pockball.pockball.db_models;

import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.types.BallType;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    public String playerId;
    public BallType ballType;
    public List<String> score = new ArrayList<>();
    public List<EventModel> events = new ArrayList<>();
    public boolean idle = false;

    public PlayerModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public PlayerModel(String playerId) {
        this.playerId = playerId;
    }

    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }

    public void addBallToScore(String ballId, BallComponent ball) {
        if (ballType == null) this.ballType = ball.type;

        score.add(ballId);
    }

    public void addShot(ShotEvent shot) {
        if (events == null) events = new ArrayList<>();
        events.add(shot);
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "playerId='" + playerId + '\'' +
                ", ballType=" + ballType +
                ", score=" + score +
                ", events=" + events +
                '}';
    }
}
