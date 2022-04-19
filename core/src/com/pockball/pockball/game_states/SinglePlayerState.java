package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.db_models.BallTypeModel;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

import java.util.List;

public class SinglePlayerState implements State {

    private final Entity playerEntity;
    private final PlayerComponent player;
    private final ScoreComponent score;
    private int numberOfShots = 0;
    private float gameVolume;
    private int lastHole;
    private boolean hasAimed = false;

    public SinglePlayerState() {
        // Set up player
        playerEntity = EntityFactory.getInstance().createPlayer("singlePlayerPlayer");
        gameVolume = 0;
        score = playerEntity.getComponent(ScoreComponent.class);
        player = playerEntity.getComponent(PlayerComponent.class);
    }

    @Override
    public void ballIntoHole(BallType ballType, int holeID) {
        switch (ballType) {
            case WHITE:
                incNumberOfShots();
                break;

            case BLACK:
                if (score.balls < 14 || lastHole != holeID) {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.GAMEOVER, ScreenModel.Screen.SINGLEPLAYER);
                    System.out.println("Player lost! Black ball into hole.");
                } else {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.WINNER, ScreenModel.Screen.SINGLEPLAYER);
                    System.out.println("Player won!");
                }
                break;


            default:
                score.balls++;
                Engine.getInstance().givePowerup();

                if (score.balls == 14) {
                    switch (holeID) {
                        case 0:
                            lastHole = 5;
                            break;
                        case 1:
                            lastHole = 4;
                            break;
                        case 2:
                            lastHole = 3;
                            break;
                        case 3:
                            lastHole = 2;
                            break;
                        case 4:
                            lastHole = 1;
                            break;
                        case 5:
                            lastHole = 0;
                            break;
                    }
                }
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
    public void addEvent(EventModel event) {

    }

    @Override
    public void fireOpponentEventChange(List<EventModel> eventModelList) {

    }

    @Override
    public void fireOpponentIsIdle() {

    }

    @Override
    public void fireBallTypeSet(BallTypeModel ballTypeModel) {

    }

    @Override
    public void fireHostTurn(boolean hostTurn) {
    }

    @Override
    public void setIdle(boolean idle) {
    }

    @Override
    public boolean getIdle() {
        return false;
    }

    @Override
    public boolean canPerformAction() {
        return true;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void incNumberOfShots() {
        numberOfShots++;
    }

    @Override
    public void reset() {
        numberOfShots = 0;
        hasAimed = false;
    }

    @Override
    public boolean getIsMyTurn() {
        return true;
    }

    @Override
    public void setHasAimed(boolean aimed) {
        hasAimed = aimed;
    }

    @Override
    public boolean hasAimed() {
        return hasAimed;
    }
}


