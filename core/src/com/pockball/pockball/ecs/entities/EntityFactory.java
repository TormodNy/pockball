package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;

public class EntityFactory {
    private static EntityFactory entityFactory;
    private BallFactory ballFactory;
    private CueFactory cueFactory;

    private EntityFactory() {
        ballFactory = BallFactory.getInstance();
        cueFactory = CueFactory.getInstance();
    }

    public static EntityFactory getInstance() {
        if (entityFactory == null)
            entityFactory = new EntityFactory();
        return entityFactory;
    }

    public Entity createBall(float x, float y, int ballID) {
        return ballFactory.createBall(x,y,ballID);
    }

    public Entity createCue(Entity ball) {
        return cueFactory.createCue(ball);
    }

}
