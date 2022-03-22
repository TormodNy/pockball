package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Interpolation;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.NumberOfShotsComponent;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;

public class SinglePlayerState implements State {

    private final Entity playerEntity;
    private final PlayerComponent player;
    private final ScoreComponent score;
    private final NumberOfShotsComponent numberOfShots;

    public SinglePlayerState() {
        // Set up player
        playerEntity = EntityFactory.getInstance().createPlayer("singlePlayerPlayer");

        score = playerEntity.getComponent(ScoreComponent.class);
        player = playerEntity.getComponent(PlayerComponent.class);
        numberOfShots = playerEntity.getComponent(NumberOfShotsComponent.class);
    }

    @Override
    public void ballIntoHole(BallType ballType) {
        switch (ballType) {
            case WHITE:
                System.out.println("Next player! White ball into hole.");
                break;

            case BLACK:
                System.out.println("Player lost! Black ball into hole.");
                break;

            default:
                System.out.println(ballType.toString() + " ball into hole.");
                score.balls++;
        }
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[1];
        players[0] = playerEntity;
        return players;
    }

    @Override
    public int getNumberOfShots() {
        return numberOfShots.numberOfShots;
    }
}


