package com.pockball.pockball.db_models;

import com.badlogic.gdx.math.Vector2;

public class ShotModel {
    public Vector2 force;

    public ShotModel() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public ShotModel(Vector2 force) {
        this.force = force;
    }
}
