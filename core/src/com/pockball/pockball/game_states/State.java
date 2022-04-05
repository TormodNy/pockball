package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.db_models.BallTypeModel;

import java.util.List;

public interface State {
    public void ballIntoHole(BallType ball, int holeID);

    public void changeGameVolume(float gameVolume);

    public float getGameVolume();

    public Entity[] getPlayers();

    public void addEvent(EventModel event);

    public void fireOpponentEventChange(List<EventModel> eventModelList);

    public void fireOpponentIsIdle();

    public void fireBallTypeSet(BallTypeModel ballTypeModel);

    public void fireHostTurn(boolean hostTurn);

    public void setIdle(boolean idle);

    public boolean getIdle();

    public boolean canPerformAction();

    public int getNumberOfShots();

    void incNumberOfShots();

    void reset();

    public boolean getIsMyTurn();
}
