package com.pockball.pockball.db_models;

import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.types.BallType;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    public String playerId;
    public BallType ballType;
    public List<String> score;

    public PlayerModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public PlayerModel(String playerId) {
        this.playerId = playerId;
        this.score = new ArrayList<>();
    }

    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }

    public void addBallToScore(String ballId, BallComponent ball) {
        if (ballType == null) this.ballType = ball.type;

        score.add(ballId);
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "playerId='" + playerId + '\'' +
                ", ballType=" + ballType +
                '}';
    }
}
