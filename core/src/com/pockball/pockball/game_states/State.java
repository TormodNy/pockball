package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.types.BallType;

public abstract class State {
    protected boolean showPowerups = false;

    public abstract void ballIntoHole(BallType ball);

    public abstract Entity[] getPlayers();

    public boolean getShowPowerups() {
        return showPowerups;
    }

    public void setShowPowerups(boolean visible) {
        showPowerups = visible;
    }
}
