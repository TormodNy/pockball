package com.pockball.pockball.screens.settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pockball.pockball.assets.SoundController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;
import com.pockball.pockball.screens.multiplayer.MultiplayerController;

public class SettingsView extends ScreenView {

    public SettingsView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);

    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label settingsTitle = new Label("Settings: ", assetsController.getSkin());
        Label volumeTitle = new Label("Volume ", assetsController.getSkin());
        Slider volumeSlider = new Slider(0, 100, 1, false, assetsController.getSkin());
        volumeSlider.setValue(SoundController.getInstance().getGameVolume()*100);
        volumeSlider.getStyle().knob.setMinHeight(assetScaler * 30);
        volumeSlider.getStyle().knob.setMinWidth(assetScaler * 30);

        settingsTitle.setFontScale(assetScaler);
        volumeTitle.setFontScale(assetScaler);

        table.add(settingsTitle);
        table.row().padTop(50);
        table.add(volumeTitle);
        table.row().padTop(10);
        table.add(volumeSlider).width(400*assetScaler).height(50*assetScaler);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                SoundController.getInstance().changeGameVolume(volumeSlider.getValue()/100);
            }
        });

        if (previousScreen == ScreenModel.Screen.MAINMENU) {
            TextButton mainMenuButton = new TextButton(" MAIN MENU ", assetsController.getSkin());
            mainMenuButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(mainMenuButton).uniformX();

            Util.addPathToButton(screenController, mainMenuButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.SETTINGS);
        }
        else {
            TextButton quitButton = new TextButton(" QUIT ", assetsController.getSkin());
            quitButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(quitButton).uniformX();

            TextButton resumeButton = new TextButton(" RESUME ", assetsController.getSkin());
            resumeButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(resumeButton).uniformX();

            if (previousScreen == ScreenModel.Screen.SINGLEPLAYER) {
                Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.SETTINGS);
                Util.addPathToButton(screenController, resumeButton, ScreenModel.Screen.SINGLEPLAYER, ScreenModel.Screen.SETTINGS);
            } else {
                quitButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        MultiplayerController.getInstance().removeRoom();
                        screenController.changeScreen(ScreenModel.Screen.MAINMENU, ScreenModel.Screen.SETTINGS);
                    }
                });
                Util.addPathToButton(screenController, resumeButton, ScreenModel.Screen.MULTIPLAYER, ScreenModel.Screen.SETTINGS);
            }

        }
    }

    @Override
    public void render(float delta){
        super.render(delta);

        //update multiplayer timer in paused state
        Context.getInstance().getState().updateTimer(delta);
    }
}
