package com.pockball.pockball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.pockball.pockball.screens.join_game_room.JoinGameController;
import com.pockball.pockball.screens.join_game_room.JoinGameView;
import com.pockball.pockball.screens.gameover.GameoverView;
import com.pockball.pockball.screens.main_menu.MainMenuView;
import com.pockball.pockball.screens.create_game_room.CreateGameRoomController;
import com.pockball.pockball.screens.create_game_room.CreateGameRoomView;
import com.pockball.pockball.screens.multiplayer.MultiplayerController;
import com.pockball.pockball.screens.multiplayer.MultiplayerView;
import com.pockball.pockball.screens.settings.SettingsView;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;
import com.pockball.pockball.screens.singleplayer.SinglePlayerView;
import com.pockball.pockball.screens.won.WinnerView;

public class ScreenController implements Disposable {

    private static ScreenController screenControllerInstance = null;
    private Screen screen;
    private Screen previousScreen;

    private ScreenController() {}

    public static ScreenController getInstance() {
        if (screenControllerInstance == null) {
            screenControllerInstance = new ScreenController();
        }
        return screenControllerInstance;
    }

    // Add additional screens into this function
    public void changeScreen(final ScreenModel.Screen screenType, ScreenModel.Screen previousScreenType) {
        Gdx.app.postRunnable(() -> {
            switch(screenType) {
                case SINGLEPLAYER:
                    if (previousScreenType == ScreenModel.Screen.SETTINGS) {
                        this.setScreen(previousScreen);
                        previousScreen = null;
                    }
                    else {
                        SinglePlayerView singlePlayerView = new SinglePlayerView(this, previousScreenType);
                        this.setScreen(singlePlayerView);
                    }
                    break;
                case MULTIPLAYER:
                    if (previousScreenType == ScreenModel.Screen.SETTINGS) {
                        this.setScreen(previousScreen);
                        previousScreen = null;
                    }
                    else {
                        MultiplayerView multiplayerView = new MultiplayerView(this, previousScreenType);
                        this.setScreen(multiplayerView);
                    }
                    break;
                case JOIN_GAME:
                    JoinGameView joinGameView = new JoinGameView(this, previousScreenType);
                    this.setScreen(joinGameView);
                    break;
                case MAINMENU:
                    MainMenuView mainMenuView = new MainMenuView(this);
                    this.setScreen(mainMenuView);
                    previousScreen = null;
                    break;
                case SETTINGS:
                    if (previousScreenType == ScreenModel.Screen.SINGLEPLAYER || previousScreenType == ScreenModel.Screen.MULTIPLAYER) {
                        previousScreen = screen;
                    }
                    SettingsView settingsView = new SettingsView(this, previousScreenType);
                    this.setScreen(settingsView);
                    break;
                case CREATE_GAME:
                    CreateGameRoomView createGameRoomView = new CreateGameRoomView(this, previousScreenType);
                    this.setScreen(createGameRoomView);
                    break;
                case GAMEOVER:
                    GameoverView gameoverView = new GameoverView(this, previousScreenType);
                    this.setScreen(gameoverView);
                    break;
                case WINNER:
                    WinnerView winnerView = new WinnerView(this, previousScreenType, previousScreenType == ScreenModel.Screen.MULTIPLAYER);
                    this.setScreen(winnerView);
                    break;
            }
        });
    }

    public void setScreen(Screen newScreen) {
        if (screen != previousScreen) {
            screen.hide();
            screen.dispose();
        }
        screen = newScreen;
        if (screen != null) {
            screen.show();
        }
    }

    @Override
    public void dispose() {
        if (this.screen != null) {
            this.screen.dispose();
        }
        this.screen = null;
    }

    public Screen getScreen() {
        return screen;
    }
}
