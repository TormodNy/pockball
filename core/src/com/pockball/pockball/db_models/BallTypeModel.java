package com.pockball.pockball.db_models;

import com.pockball.pockball.ecs.types.BallType;

public class BallTypeModel {
    public BallType host;
    public BallType client;

    public BallTypeModel() { }

    public BallTypeModel(BallType host, BallType client) {
        this.host = host;
        this.client = client;
    }
}
