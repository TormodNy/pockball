package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.game_modes.GameModeContext;

public class PhysicsSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<DirectionComponent> directionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private long timeLastMovement;
    private boolean moving;

    public PhysicsSystem() {
        super(Family.all(PhysicsBodyComponent.class, PositionComponent.class, DirectionComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        directionMapper = ComponentMapper.getFor(DirectionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        timeLastMovement = 0;
        moving = false;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        DirectionComponent direction = directionMapper.get(entity);
        PhysicsBodyComponent physics = physicsBodyMapper.get(entity);


        // Notify when all balls have stopped
        if (Engine.getInstance().isBall(entity)) {
            long now = System.currentTimeMillis();

            if (physics.body.getLinearVelocity().len() > 0.1f) {
                timeLastMovement = now;
            }

            if (moving && timeLastMovement + 1000L < now) {
                moving = false;
                GameModeContext.getInstance().getState().setIdle(!moving);
            } else if (!moving && timeLastMovement + 1000L >= now) {
                moving = true;
                GameModeContext.getInstance().getState().setIdle(!moving);
            }
        }

        position.position.set(physics.body.getPosition());
        direction.rotation = physics.body.getAngle() * 57.3f;
    }
}
