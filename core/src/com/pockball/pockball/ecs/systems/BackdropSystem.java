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
import com.pockball.pockball.ecs.components.BackdropComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.PowerupComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.State;

public class BackdropSystem extends IteratingSystem {
    private final ComponentMapper<SpriteComponent> spriteMapper;

    private State state;

    public BackdropSystem() {
        super(Family.all(SpriteComponent.class, BackdropComponent.class).get());

        state = Context.getInstance().getState();

        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = spriteMapper.get(entity);

        sprite.visible = state.getShowPowerups();
    }
}
