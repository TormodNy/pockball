package com.pockball.pockball.screens.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;

public class GameoverView implements Screen {
    private final Stage stage;
    private final AssetsController assetsController;
    private final ScreenController screenController;
    private final float assetScaler;


    public GameoverView(ScreenController screenController, ScreenModel.Screen screenModel) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.screenController = screenController;
        this.assetsController = AssetsController.getInstance();
        assetScaler = assetsController.getAssetScaler();
    }

    @Override
    public void show(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameoverTitle = new Label("GAME OVER! ", assetsController.getSkin());
        gameoverTitle.setFontScale(assetScaler);
        table.add(gameoverTitle);

        TextButton quitButton = new TextButton("QUIT", assetsController.getSkin());
        quitButton.getLabel().setFontScale(assetScaler);

        table.row().padTop(50);
        table.add(quitButton).uniformX();

        Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.GAMEOVER);
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

