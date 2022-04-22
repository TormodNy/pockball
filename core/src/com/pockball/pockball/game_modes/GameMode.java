package com.pockball.pockball.game_modes;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.db_models.BallTypeModel;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.ecs.types.BallType;

import java.util.List;

public interface GameMode {
    void ballIntoHole(BallType ball, int holeID);

    Entity[] getPlayers();

    void addEvent(EventModel event);

    void fireOpponentEventChange(List<EventModel> eventModelList);

    void fireOpponentIsIdle();

    void fireBallTypeSet(BallTypeModel ballTypeModel);

    void fireHostTurn(boolean hostTurn);

    void setIdle(boolean idle);

    boolean getIdle();

    boolean canPerformAction();

    int getNumberOfShots();

    void incNumberOfShots();

    void reset();

    boolean getIsMyTurn();

    void setHasAimed(boolean aimed);

    boolean hasAimed();

    void updateTimer(float delta);
}
