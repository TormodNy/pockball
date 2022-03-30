package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.gameover.GameoverView;

public class SinglePlayerState implements State {

    private final Entity playerEntity;
    private final PlayerComponent player;
    private final ScoreComponent score;
    private int numberOfShots = 0;
    private float gameVolume;

    public SinglePlayerState() {
        // Set up player
        playerEntity = EntityFactory.getInstance().createPlayer("singlePlayerPlayer");
        gameVolume = 0;
        score = playerEntity.getComponent(ScoreComponent.class);
        player = playerEntity.getComponent(PlayerComponent.class);
    }

    @Override
    public void ballIntoHole(BallType ballType) {
        switch (ballType) {
            case WHITE:
                System.out.println("Penalty point! White ball into hole.");
                incNumberOfShots();
                break;

            case BLACK:
                if (score.balls < 14) {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.GAMEOVER, ScreenModel.Screen.SINGLEPLAYER);
                    System.out.println("Player lost! Black ball into hole.");
                }
                else {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.WINNER, ScreenModel.Screen.SINGLEPLAYER);
                    System.out.println("Player won!");
                }
                break;


            default:
                System.out.println(ballType.toString() + " ball into hole.");
                score.balls++;
        }
    }

    @Override
    public void changeGameVolume(float gameVolume) {
        this.gameVolume = gameVolume;
    }

    @Override
    public float getGameVolume() {
        return gameVolume;
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[1];
        players[0] = playerEntity;
        return players;
    }

    @Override
    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void incNumberOfShots() {
        numberOfShots++;
    }

    @Override
    public void reset() {
        numberOfShots = 0;
    }
}


