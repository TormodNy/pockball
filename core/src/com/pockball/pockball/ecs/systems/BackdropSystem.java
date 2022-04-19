package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pockball.pockball.ecs.components.BackdropComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.screens.GameController;

public class BackdropSystem extends IteratingSystem {
    private final ComponentMapper<SpriteComponent> spriteMapper;

    public BackdropSystem() {
        super(Family.all(SpriteComponent.class, BackdropComponent.class).get());

        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = spriteMapper.get(entity);

        sprite.visible = GameController.currentController.getShowPowerups();
    }
}
