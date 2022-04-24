package com.pockball.pockball.game_modes;

public class GameModeContext {

    // Singleton
    private static GameModeContext gameModeContext;
    private GameMode gameMode;

    public GameModeContext() {
        // Standard value
        gameMode = new SinglePlayerGameMode();
    }

    public static GameModeContext getInstance() {
        if (gameModeContext == null) {
            gameModeContext = new GameModeContext();
        }
        return gameModeContext;
    }

    public void setState(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getState() {
        return gameMode;
    }
}
