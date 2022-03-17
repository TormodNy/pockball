package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.components.TimeoutComponent;

public class PlayerSystem extends IteratingSystem {
    private final ComponentMapper<ScoreComponent> scoreMapper;
    private final ComponentMapper<TimeoutComponent> timeoutMapper;
    private final ComponentMapper<PlayerComponent> playerMapper;

    public PlayerSystem() {
        super(Family.all(PlayerComponent.class, ScoreComponent.class, TimeoutComponent.class).get());

        scoreMapper = ComponentMapper.getFor(ScoreComponent.class);
        timeoutMapper = ComponentMapper.getFor(TimeoutComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ScoreComponent score = scoreMapper.get(entity);
        TimeoutComponent timeout = timeoutMapper.get(entity);

    //    System.out.println(playerMapper.get(entity).playerName + " " + score.balls + "\t" + timeout.timeout);

        timeout.timeout -= deltaTime;
    }
}
