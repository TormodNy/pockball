package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.assets.SoundController;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.GameController;

public class BallSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<BallComponent> ballMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;

    private final SoundController soundController;

    private boolean justTouched = false;
    private Vector2 powerRef = new Vector2(0, 0);

    public BallSystem() {
        // Må legge til NumberOfShotsComponent.class her, men da funker det heller ikke
        super(Family.all(PositionComponent.class, PhysicsBodyComponent.class, BallComponent.class, PlaceEntityComponent.class).get());

        soundController = SoundController.getInstance();

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

        boolean hasAimed = Context.getInstance().getState().hasAimed();

        // Stop balls when they are slow (Drag is not enough)
        if (physics.body.getLinearVelocity().len() <= 0.15f) {
            physics.body.setLinearVelocity(physics.body.getLinearVelocity().scl(0.8f));
            physics.body.setAngularVelocity(physics.body.getAngularVelocity() * 0.8f);
        }

        // Only shoot white ball with almost no speed
        boolean canShoot = ball.type.equals(BallType.WHITE)
                && Context.getInstance().getState().canPerformAction()
                && physics.body.getLinearVelocity().len() <= 0.01f
                && !placeEntity.placeable
                && !GameController.currentController.getShowPowerups();
        if (canShoot) {
            if (Gdx.input.isTouched() && (Gdx.input.getY() >= 40 || justTouched)) {
                Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                Vector2 inputInWorld = new Vector2(input.x, input.y);
                Vector2 origin = new Vector2(position.position.x, position.position.y).add(ball.radius, ball.radius);

                if (!hasAimed) {
                    // If first touch, set direction
                    ball.dir = inputInWorld.sub(origin).scl(-1);
                }
                if (hasAimed && !justTouched) {
                    // If first touch after aim, set reference for power
                    powerRef = inputInWorld.sub(origin);
                }

                if (ball.dir.len() != 0) {
                    justTouched = true;
                }

                if (hasAimed) {
                    // Set power by distance dragged from reference
                    inputInWorld = new Vector2(input.x, input.y);
                    ball.power = inputInWorld.sub(origin).sub(powerRef);
                    ball.power.clamp(0.1f, 3f);
                }
            } else if (justTouched) {
                if (hasAimed) {
                    if (ball.power.len() <= 0) return;

                    // Shoot ball in direction with power
                    float force = 1500;
                    Vector2 directionWithForce = ball.dir.nor().scl(force * ball.power.len());
                    Engine.getInstance().shootBallWithForce(directionWithForce, true);
                    ball.power = new Vector2(0,0);
                    ball.dir = new Vector2(0, 0);

                    // Increments number of shots for singleplayer
                    Context.getInstance().getState().incNumberOfShots();

                    // Play sound
                    soundController.playSound("cueHit", ball.power.len() / 3);

                    Context.getInstance().getState().setHasAimed(false);
                } else {
                    Context.getInstance().getState().setHasAimed(true);
                }

                justTouched = false;
            }
        }
    }
}
