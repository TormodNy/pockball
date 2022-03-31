package com.pockball.pockball.screens;

public abstract class GameController {
    public static GameController currentController;

    protected boolean showPowerups = false;

    public boolean getShowPowerups() {
        return showPowerups;
    }

    public void setShowPowerups(boolean visible) {
        showPowerups = visible;
    }
}