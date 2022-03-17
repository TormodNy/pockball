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

    private ScreenController() {}

    public static ScreenController getInstance() {
        if (screenControllerInstance == null) {
            screenControllerInstance = new ScreenController();
        }
        return screenControllerInstance;
    }

    // Add additional screens into this function
    public void changeScreen(final ScreenModel.Screen screen) {
        Gdx.app.postRunnable(() -> {
            switch(screen) {
                case SINGLEPLAYER:
                    SinglePlayerController singlePlayerController = SinglePlayerController.getInstance();
                    SinglePlayerView singlePlayerView = new SinglePlayerView(singlePlayerController);
                    this.setScreen(singlePlayerView);
                    break;
                case MULTIPLAYER:
                    MultiplayerController multiplayerController = MultiplayerController.getInstance();
                    MultiplayerView multiplayerView = new MultiplayerView(multiplayerController);
                    this.setScreen(multiplayerView);
                    break;
                case MAINMENU:
                    MainMenuView mainMenuView = new MainMenuView(this);
                    this.setScreen(mainMenuView);
                    break;
                case SETTINGS:
                    SettingsView settingsView = new SettingsView(this);
                    this.setScreen(settingsView);
                    break;
            }
        });
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) {
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
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
