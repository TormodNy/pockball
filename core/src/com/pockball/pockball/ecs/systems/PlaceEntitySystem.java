package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.types.BallType;

public class PlaceEntitySystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;
    private final ComponentMapper<BallComponent> ballMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;

    public PlaceEntitySystem() {
        super(Family.all(PlaceEntityComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        PhysicsBodyComponent physicsBody = physicsBodyMapper.get(entity);
        PlaceEntityComponent placeEntity = placeEntityMapper.get(entity);
        BallComponent ball = ballMapper.get(entity);
        SpriteComponent sprite = spriteMapper.get(entity);

        if (Gdx.input.justTouched() && ball.type.equals(BallType.WHITE) && placeEntity.placeable) {
            placeEntity.placeable = false;
            Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            physicsBody.body.setTransform(input.x - ball.radius, input.y - ball.radius, 0);

            sprite.sprite.setAlpha(1);

        }

    }
}
