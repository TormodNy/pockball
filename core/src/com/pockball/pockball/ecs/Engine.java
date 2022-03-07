package com.pockball.pockball.ecs;

import com.badlogic.ashley.core.PooledEngine;
import com.pockball.pockball.ecs.entities.BallFactory;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.systems.RenderSystem;

public class Engine extends PooledEngine {
    private static Engine engineInstance;

    private final EntityFactory entityFactory;


    private Engine() {
        entityFactory = EntityFactory.getInstance();
    }

    public static Engine getInstance() {
        if (engineInstance == null)
            engineInstance = new Engine();
        return engineInstance;
    }

    public void initializeEngine() {
        engineInstance.removeAllEntities();
        engineInstance.clearPools();
        engineInstance.addSystem(new RenderSystem());

    }


}
