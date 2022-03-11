package com.pockball.pockball.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pockball.pockball.ecs.entities.BallFactory;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.systems.BallSystem;
import com.pockball.pockball.ecs.systems.PhysicsSystem;
import com.pockball.pockball.ecs.systems.RenderSystem;

public class Engine extends PooledEngine {
    private static Engine engineInstance;

    private final EntityFactory entityFactory;

    private World world;
    private Body walls;

    private Engine() {
        entityFactory = EntityFactory.getInstance();
    }

    public static Engine getInstance() {
        if (engineInstance == null)
            engineInstance = new Engine();
        return engineInstance;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        float timeStep = 1f / 60f;
        world.step(timeStep, 8, 3);
    }

    public void initializeEngine() {
        engineInstance.removeAllEntities();
        engineInstance.clearPools();
        engineInstance.addSystem(new RenderSystem());
        engineInstance.addSystem(new PhysicsSystem());
        engineInstance.addSystem(new BallSystem());

        createWorld();

        // Create balls
        for (int i = 1; i <= 15; i++) {
            Entity ball = entityFactory.createBall(i, 0, i);
            engineInstance.addEntity(ball);
        }

        Entity ball = entityFactory.createBall(12.5f, 7.5f, 0);
        engineInstance.addEntity(ball);
    }

    private void createWorld () {
        // Create world
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);

        // Create walls
        BodyDef wallsDef = new BodyDef();
        wallsDef.position.set(new Vector2(-0.5f, -0.5f)); // TODO: WHY IS THIS 0.5f OFF?

        Vector2[] vs = new Vector2[4];
        vs[0] = new Vector2(0, 0);
        vs[1] = new Vector2(25, 0);
        vs[2] = new Vector2(25, 15);
        vs[3] = new Vector2(0, 15);

        ChainShape chain = new ChainShape();
        chain.createLoop(vs);
        walls = world.createBody(wallsDef);
        walls.createFixture(chain, 0);
    }
}
