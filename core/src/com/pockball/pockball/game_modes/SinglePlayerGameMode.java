package com.pockball.pockball.game_modes;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.db_models.BallTypeModel;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

import java.util.List;

public class SinglePlayerGameMode implements GameMode {

    private final Entity playerEntity;
    private final ScoreComponent score;
    private int numberOfShots = 0;
    private int winnerHole;
    private boolean hasAimed = false;
    private boolean idle;

    public SinglePlayerGameMode() {
        // Set up player
        playerEntity = EntityFactory.getInstance().createPlayer("singlePlayerPlayer");
        score = playerEntity.getComponent(ScoreComponent.class);
        this.idle = true;
    }

    @Override
    public void ballIntoHole(BallType ballType, int holeID) {
        switch (ballType) {
            case WHITE:
                incNumberOfShots();
                break;

            case BLACK:
                if (score.balls >= 14 && winnerHole == holeID) {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.WINNER,
                            ScreenModel.Screen.SINGLEPLAYER);
                } else {
                    ScreenController.getInstance().changeScreen(ScreenModel.Screen.GAMEOVER,
                            ScreenModel.Screen.SINGLEPLAYER);
                }
                break;

            default:
                score.balls++;
                Engine.getInstance().giveInitialPowerup();
                // update the hole opposite of the last hole a ball fell into
                winnerHole = ((holeID + 3) % 6);
        }
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
        this.idle = idle;
    }

    @Override
    public boolean getIdle() {
        return this.idle;
    }

    @Override
    public boolean canPerformAction() {
        return getIdle();
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

    @Override
    public void updateTimer(float delta) {

    }
}
