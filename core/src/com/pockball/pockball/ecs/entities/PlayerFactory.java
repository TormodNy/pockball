package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.components.TimeoutComponent;

public class PlayerFactory {
    private static PlayerFactory ballFactory;

    private PlayerFactory() {}

    public static PlayerFactory getInstance() {
        if (ballFactory == null)
            ballFactory = new PlayerFactory();
        return ballFactory;
    }

    public Entity createPlayer(String playerName) {
        ScoreComponent score = Engine.getInstance().createComponent(ScoreComponent.class);
        TimeoutComponent timeout = Engine.getInstance().createComponent(TimeoutComponent.class);
        PlayerComponent playerComponent = Engine.getInstance().createComponent(PlayerComponent.class);
        playerComponent.playerName = playerName;

        Entity player = Engine.getInstance().createEntity();
        player.add(score);
        player.add(timeout);
        player.add(playerComponent);

        return player;
    }
}
