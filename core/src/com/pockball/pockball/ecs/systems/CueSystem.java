package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.CueComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.types.CueType;
import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.screens.GameController;

public class CueSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<DirectionComponent> directionMapper;
    private final ComponentMapper<CueComponent> cueMapper;
    private final ComponentMapper<SizeComponent> sizeMapper;
    private final ComponentMapper<BallComponent> ballMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;

    public CueSystem() {
        super(Family.all(CueComponent.class, PositionComponent.class, DirectionComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        directionMapper = ComponentMapper.getFor(DirectionComponent.class);
        cueMapper = ComponentMapper.getFor(CueComponent.class);
        sizeMapper = ComponentMapper.getFor(SizeComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
        physicsMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        DirectionComponent direction = directionMapper.get(entity);
        CueComponent cue = cueMapper.get(entity);
        SizeComponent size = sizeMapper.get(entity);
        BallComponent ball = ballMapper.get(cue.ball);
        PositionComponent ballPos = positionMapper.get(cue.ball);
        PhysicsBodyComponent physics = physicsMapper.get(cue.ball);
        PlaceEntityComponent placeEntity = placeEntityMapper.get(cue.ball);

        // Position cue at ball when shooting
        boolean canShoot = Gdx.input.isTouched() &&
                !placeEntity.placeable &&
                !GameController.currentController.getShowPowerups() &&
                GameModeContext.getInstance().getState().canPerformAction() &&
                Gdx.input.getY() / AssetsController.getInstance().getAssetScaler() >= 100 &&
                ball.dir.len() != 0 &&
                physics.body.getLinearVelocity().len() <= 0.01f;
        boolean hasAimed = GameModeContext.getInstance().getState().hasAimed();
        boolean isLine = cue.cueType.equals(CueType.LINE);

        if (canShoot && !(isLine && hasAimed)) {
            if (isLine) {
                direction.rotation = ball.dir.cpy().scl(-1).angleDeg();
            } else {
                direction.rotation = ball.dir.angleDeg();
            }
            Vector2 dir = new Vector2(ball.dir).nor();
            position.position.set(ballPos.position.x - size.width + ball.radius - 0.01f, ballPos.position.y - 0.09f)
                    .sub(dir.scl(0.7f + ball.power.len()));
        } else if (!hasAimed || isLine) {
            position.position.set(100, 100);
        }
    }
}
