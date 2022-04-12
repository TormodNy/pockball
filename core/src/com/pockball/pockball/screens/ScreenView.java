package com.pockball.pockball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;

public abstract class ScreenView implements Screen {
    protected final Stage stage;

    protected final ScreenController screenController;
    protected final ScreenModel.Screen previousScreen;

    protected final AssetsController assetsController;
    protected final float assetScaler;


    public ScreenView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.screenController = screenController;
        this.previousScreen = previousScreen;

        this.assetsController = AssetsController.getInstance();
        assetScaler = assetsController.getAssetScaler();
    }

    @Override
    public void show() {

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
        stage.getActors().clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
