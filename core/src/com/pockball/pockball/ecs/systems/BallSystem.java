package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.types.BallType;

public class BallSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<BallComponent> ballMapper;

    private boolean justTouched = false;
    private Vector2 dir;

    public BallSystem() {
        super(Family.all(PositionComponent.class, PhysicsBodyComponent.class, BallComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        PhysicsBodyComponent physics = physicsBodyMapper.get(entity);
        BallComponent ball = ballMapper.get(entity);

        if (Gdx.input.isTouched() && ball.type.equals(BallType.WHITE) && physics.body.getLinearVelocity().len() <= 0.1f) {
            justTouched = true;

            Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 input2 = new Vector2(input.x, input.y);
            Vector2 origin = new Vector2(position.position.x, position.position.y).add(ball.radius, ball.radius);
            dir = input2.sub(origin);
            dir.clamp(0.1f, 3f);
        } else if (justTouched && ball.type.equals(BallType.WHITE)) {
            float force = 1000;
            physics.body.applyForceToCenter(dir.scl(force), true);
            justTouched = false;
        }
    }
}
