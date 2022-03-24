package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.db_models.ShotModel;
import com.pockball.pockball.ecs.types.BallType;

import java.util.List;

public interface State {
    public void ballIntoHole(BallType ball);

    public Entity[] getPlayers();

    public void shoot(Vector2 force);

    public void fireOpponentShotsChange(List<ShotModel> shotModelList);

    public void setHostTurn(boolean hostTurn);

}
