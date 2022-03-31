package com.pockball.pockball.db_models;

import com.badlogic.gdx.math.Vector2;

public class ShotEvent extends EventModel {
    public float x, y;

    public ShotEvent() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public ShotEvent(Vector2 force) {
        this.type = Type.SHOT;
        this.x = force.x;
        this.y = force.y;
    }
}
