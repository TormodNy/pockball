package com.pockball.pockball.screens.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;

public class SettingsView implements Screen {

    private Stage stage;
    private AssetsController assetsController;
    private ScreenController screenController;


    public SettingsView(ScreenController screenController) {
        this.screenController = screenController;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.assetsController = AssetsController.getInstance();
    }


    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label settingsTitle = new Label("Settings: ", assetsController.getSkin());
        TextButton mainMenuButton = new TextButton("MAIN MENU", assetsController.getSkin());
        Label volumeTitle = new Label("Volume ", assetsController.getSkin());
        Slider volumeSlider = new Slider(0, 100, 1, false, assetsController.getSkin());

        table.add(settingsTitle);
        table.row().padTop(50);
        table.add(volumeTitle);
        table.row().padTop(10);
        table.add(volumeSlider);
        table.row().padTop(50);
        table.add(mainMenuButton).uniformX();

        Util.addPathToButton(screenController, mainMenuButton, ScreenModel.Screen.MAINMENU);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                // Adjust the value of volume in here
                System.out.println(volumeSlider.getValue());
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
