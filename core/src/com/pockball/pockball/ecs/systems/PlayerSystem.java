package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.components.TimeoutComponent;

public class PlayerSystem extends IteratingSystem {
    private final ComponentMapper<TimeoutComponent> timeoutMapper;

    public PlayerSystem() {
        super(Family.all(PlayerComponent.class, ScoreComponent.class, TimeoutComponent.class).get());

        timeoutMapper = ComponentMapper.getFor(TimeoutComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TimeoutComponent timeout = timeoutMapper.get(entity);

        timeout.timeout -= deltaTime;
    }
}
