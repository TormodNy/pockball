package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;

public class EntityFactory {
    private static EntityFactory entityFactory;
    private BallFactory ballFactory;
    private HoleFactory holeFactory;

    private EntityFactory() {
        ballFactory = BallFactory.getInstance();
        holeFactory = HoleFactory.getInstance();
    }

    public static EntityFactory getInstance() {
        if (entityFactory == null)
            entityFactory = new EntityFactory();
        return entityFactory;
    }

    public Entity createBall(float x, float y, int ballID) {
        return ballFactory.createBall(x,y,ballID);
    }


    public Entity createHole(float x, float y, int holeID) {
        return holeFactory.createHole(x, y, holeID);
    }
}
