package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;

public class RenderSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;
    private Array<Entity> renderQueue;

    private final ComponentMapper<SpriteComponent> spriteMapper;
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<SizeComponent> sizeMapper;
    private final ComponentMapper<DirectionComponent> directionMapper;

    public RenderSystem() {
        super(Family.all(SpriteComponent.class, PositionComponent.class, SizeComponent.class, DirectionComponent.class).get());

        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        sizeMapper = ComponentMapper.getFor(SizeComponent.class);
        directionMapper = ComponentMapper.getFor(DirectionComponent.class);

        spriteBatch = new SpriteBatch();
        renderQueue = new Array<Entity>();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ScreenUtils.clear(0, 0, 0, 1);

        spriteBatch.setProjectionMatrix(PockBall.camera.combined);

        spriteBatch.begin();

        for (Entity entity : renderQueue) {
            SpriteComponent spriteComponent = spriteMapper.get(entity);
            PositionComponent positionComponent = positionMapper.get(entity);
            SizeComponent sizeComponent = sizeMapper.get(entity);
            DirectionComponent directionComponent = directionMapper.get(entity);

            spriteComponent.sprite.setRotation(directionComponent.rotation);
            spriteComponent.sprite.setPosition(positionComponent.position.x, positionComponent.position.y);
            spriteComponent.sprite.setSize(sizeComponent.width, sizeComponent.height);

            spriteComponent.sprite.draw(spriteBatch);
        }

        spriteBatch.end();
        renderQueue.clear();
    }
}