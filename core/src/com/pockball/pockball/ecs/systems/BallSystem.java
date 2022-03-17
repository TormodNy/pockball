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
import com.pockball.pockball.ecs.components.HoleComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.State;

public class BallSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<BallComponent> ballMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;

    private State state;

    private boolean justTouched = false;

    public BallSystem() {
        super(Family.all(PositionComponent.class, PhysicsBodyComponent.class, BallComponent.class, PlaceEntityComponent.class).get());

        state = Context.getInstance().getState();

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        PhysicsBodyComponent physics = physicsBodyMapper.get(entity);
        BallComponent ball = ballMapper.get(entity);
        PlaceEntityComponent placeEntity = placeEntityMapper.get(entity);

        // Stop balls when they are slow (Drag is not enough)
        if (physics.body.getLinearVelocity().len() <= 0.15f) {
            physics.body.setLinearVelocity(physics.body.getLinearVelocity().scl(0.8f));
            physics.body.setAngularVelocity(physics.body.getAngularVelocity() * 0.8f);
        }

        // Only shoot white ball with almost no speed
        if (ball.type.equals(BallType.WHITE) && physics.body.getLinearVelocity().len() <= 0.01f && !placeEntity.placeable && !state.getShowPowerups()) {
            if (Gdx.input.isTouched()) {
                if (!justTouched) {
                    // If first touch, set direction
                    Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    Vector2 inputInWorld = new Vector2(input.x, input.y);
                    Vector2 origin = new Vector2(position.position.x, position.position.y).add(ball.radius, ball.radius);
                    ball.dir = inputInWorld.sub(origin);
                }
                justTouched = true;

                // Set power by distance dragged from first touch
                Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                Vector2 inputInWorld = new Vector2(input.x, input.y);
                Vector2 direction = new Vector2(ball.dir);
                Vector2 origin = new Vector2(position.position.x, position.position.y).add(ball.radius, ball.radius);
                ball.power = inputInWorld.sub(origin).sub(direction);
                ball.power.clamp(0.1f, 3f);
            } else if (justTouched) {
                // Shoot ball in direction with power
                float force = 1500;
                physics.body.applyForceToCenter(ball.dir.nor().scl(force * ball.power.len()), true);
                justTouched = false;
            }
        }
    }
}
