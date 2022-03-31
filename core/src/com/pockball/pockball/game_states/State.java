package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.ecs.types.BallType;

import java.util.List;

public interface State {
    public void ballIntoHole(BallType ball, int holeID);

    public void changeGameVolume(float gameVolume);

    public float getGameVolume();

    public Entity[] getPlayers();

    public void addEvent(EventModel event);

    public void fireOpponentEventChange(List<EventModel> eventModelList);

    public void fireOpponentIsIdle();

    public void fireBallTypeSet(BallType hostBallType, BallType opponentBallType);

    public void setHostTurn(boolean hostTurn);

    public void setIdle(boolean ready);

    public boolean canPerformAction();

    public int getNumberOfShots();

    void incNumberOfShots();

    void reset();
}
