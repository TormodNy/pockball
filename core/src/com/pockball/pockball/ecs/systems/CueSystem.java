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
import com.pockball.pockball.ecs.components.CueComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.types.BallType;

public class CueSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<DirectionComponent> directionMapper;
    private final ComponentMapper<CueComponent> cueMapper;
    private final ComponentMapper<SizeComponent> sizeMapper;
    private final ComponentMapper<BallComponent> ballMapper;

    private boolean justTouched = false;

    public CueSystem() {
        super(Family.all(CueComponent.class, PositionComponent.class, DirectionComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        directionMapper = ComponentMapper.getFor(DirectionComponent.class);
        cueMapper = ComponentMapper.getFor(CueComponent.class);
        sizeMapper = ComponentMapper.getFor(SizeComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        DirectionComponent direction = directionMapper.get(entity);
        CueComponent cue = cueMapper.get(entity);
        SizeComponent size = sizeMapper.get(entity);
        BallComponent ball = ballMapper.get(cue.ball);
        PositionComponent ballPos = positionMapper.get(cue.ball);

        if (Gdx.input.isTouched()) {
            Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 input2 = new Vector2(input.x, input.y);
            Vector2 origin = new Vector2(ballPos.position.x, ballPos.position.y).add(ball.radius, ball.radius);
            Vector2 dir = input2.sub(origin);
            dir.clamp(0.1f, 3f);

            direction.rotation = dir.angleDeg();
            position.position.set(ballPos.position.x - size.width - ball.radius, ballPos.position.y).sub(dir);
        }
    }
}
