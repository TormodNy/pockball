package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;

public class RenderSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;
    private Array<Entity> renderQueue;

    private final ComponentMapper<SpriteComponent> spriteMapper;
    private final ComponentMapper<PositionComponent> positionMapper;

    public RenderSystem() {
        super(Family.all(SpriteComponent.class, PositionComponent.class).get());

        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);

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

        spriteBatch.begin();

        for (Entity entity : renderQueue) {
            SpriteComponent spriteComponent = spriteMapper.get(entity);
            PositionComponent positionComponent = positionMapper.get(entity);
            spriteBatch.draw(spriteComponent.texture,
                    positionComponent.position.x,
                    positionComponent.position.y,
                    spriteComponent.texture.getWidth(),
                    spriteComponent.texture.getHeight());
        }

        spriteBatch.end();
        renderQueue.clear();
    }
}
