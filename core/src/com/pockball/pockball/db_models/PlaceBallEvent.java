package com.pockball.pockball.db_models;

import com.badlogic.gdx.math.Vector2;

public class PlaceBallEvent extends EventModel {
    public float x, y;

    public PlaceBallEvent() {
        // Required for calls to DataSnapshot.getValue(User.class)
        // https://firebase.google.com/docs/database/android/read-and-write
    }

    public PlaceBallEvent(Vector2 position) {
        this.type = "placeball";
        this.x = position.x;
        this.y = position.y;
    }
}
