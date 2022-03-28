package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.types.BallType;

public interface State {
    public void ballIntoHole(BallType ball);

    public void changeGameVolume(float gameVolume);

    public float getGameVolume();

    public Entity[] getPlayers();

    public int getNumberOfShots();

    void incNumberOfShots();

    void reset();
}
