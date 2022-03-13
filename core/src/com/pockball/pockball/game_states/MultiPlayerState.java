package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.types.BallType;

public class MultiPlayerState implements State {

    @Override
    public void ballIntoHole(BallType ballType) {
        System.out.println("Multiplayer ball in hole! " + ballType.toString());
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[0];
        return players;
    }
}
