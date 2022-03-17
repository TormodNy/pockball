package com.pockball.pockball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.pockball.pockball.screens.main_menu.MainMenuView;
import com.pockball.pockball.screens.multiplayer.MultiplayerController;
import com.pockball.pockball.screens.multiplayer.MultiplayerView;
import com.pockball.pockball.screens.settings.SettingsView;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;
import com.pockball.pockball.screens.singleplayer.SinglePlayerView;

import java.lang.invoke.MutableCallSite;

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
                        SinglePlayerController singlePlayerController = SinglePlayerController.getInstance();
                        SinglePlayerView singlePlayerView = new SinglePlayerView(this, singlePlayerController);
                        this.setScreen(singlePlayerView);
                    }
                    break;
                case MULTIPLAYER:
                    MultiplayerController multiplayerController = MultiplayerController.getInstance();
                    MultiplayerView multiplayerView = new MultiplayerView(multiplayerController);
                    this.setScreen(multiplayerView);
                    break;
                case MAINMENU:
                    MainMenuView mainMenuView = new MainMenuView(this);
                    this.setScreen(mainMenuView);
                    previousScreen = null;
                    break;
                case SETTINGS:
                    if (previousScreenType == ScreenModel.Screen.SINGLEPLAYER) {
                        previousScreen = screen;
                    }
                    SettingsView settingsView = new SettingsView(this, previousScreenType);
                    this.setScreen(settingsView);
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
        if (screen != null && previousScreen != newScreen) {
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
