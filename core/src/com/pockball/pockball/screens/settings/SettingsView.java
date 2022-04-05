package com.pockball.pockball.screens.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

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
        volumeSlider.setValue(Context.getInstance().getState().getGameVolume()*100);
        volumeSlider.getStyle().knob.setMinHeight(assetScaler * 30);
        volumeSlider.getStyle().knob.setMinWidth(assetScaler * 30);

        settingsTitle.setFontScale(assetScaler);
        volumeTitle.setFontScale(assetScaler);

        table.add(settingsTitle);
        table.row().padTop(50);
        table.add(volumeTitle);
        table.row().padTop(10);
        table.add(volumeSlider);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Context.getInstance().getState().changeGameVolume(volumeSlider.getValue()/100);
            }
        });

        if (previousScreen == ScreenModel.Screen.MAINMENU) {
            TextButton mainMenuButton = new TextButton("MAIN MENU", assetsController.getSkin());
            mainMenuButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(mainMenuButton).uniformX();

            Util.addPathToButton(screenController, mainMenuButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.SETTINGS);
        }
        else if (previousScreen == ScreenModel.Screen.SINGLEPLAYER) {
            TextButton quitButton = new TextButton("QUIT", assetsController.getSkin());
            quitButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(quitButton).uniformX();

            TextButton resumeButton = new TextButton("RESUME", assetsController.getSkin());
            resumeButton.getLabel().setFontScale(assetScaler);
            table.row().padTop(50);
            table.add(resumeButton).uniformX();

            Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.SETTINGS);
            Util.addPathToButton(screenController, resumeButton, ScreenModel.Screen.SINGLEPLAYER, ScreenModel.Screen.SETTINGS);
        }
    }
}
