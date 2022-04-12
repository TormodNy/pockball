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
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.game_states.Context;

public class PlaceEntitySystem extends IteratingSystem {
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;
    private final ComponentMapper<BallComponent> ballMapper;

    public PlaceEntitySystem() {
        super(Family.all(PlaceEntityComponent.class).get());

        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlaceEntityComponent placeEntity = placeEntityMapper.get(entity);
        BallComponent ball = ballMapper.get(entity);

        boolean canPlaceWhiteBall = Gdx.input.justTouched()
                && ball.type.equals(BallType.WHITE)
                && placeEntity.placeable
                && Context.getInstance().getState().canPerformAction();
        if (canPlaceWhiteBall) {
            Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            Engine.getInstance().placeWhiteBall(new Vector2(input.x, input.y), true);
        }

    }
}