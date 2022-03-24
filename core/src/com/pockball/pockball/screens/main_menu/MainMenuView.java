package com.pockball.pockball.screens.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;

public class MainMenuView implements Screen {

    private SpriteBatch sb;
    private Stage stage;
    private AssetsController assetsController;
    private ScreenController screenController;

    private Texture logo;

    public MainMenuView(ScreenController screenController) {
        this.screenController = screenController;
        this.sb = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.assetsController = AssetsController.getInstance();

        this.logo = new Texture("pockballLogo.png");
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton singleplayerButton = new TextButton("SINGLEPLAYER", assetsController.getSkin());
        TextButton multiplayerButton = new TextButton("MULTIPLAYER", assetsController.getSkin());
        TextButton joinGameButton = new TextButton("JOIN GAME", assetsController.getSkin());
        TextButton settingsButton = new TextButton("SETTINGS", assetsController.getSkin());

        table.row().padTop(50);
        table.add(singleplayerButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(multiplayerButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(joinGameButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(settingsButton).uniformX();

        Util.addPathToButton(screenController, singleplayerButton, ScreenModel.Screen.SINGLEPLAYER, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, multiplayerButton, ScreenModel.Screen.MULTIPLAYER, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, settingsButton, ScreenModel.Screen.SETTINGS, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, joinGameButton, ScreenModel.Screen.JOIN_GAME, ScreenModel.Screen.MAINMENU);
    }


    @Override
    public void render(float delta) {
        sb.begin();
        float RGBDivider = 256f;
        Gdx.gl.glClearColor(11f / RGBDivider, 137f / RGBDivider, 1f / RGBDivider, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(logo, (PockBall.WIDTH*32/2f) - (logo.getWidth()/4f), (PockBall.HEIGHT*32f * (2f/3f)), logo.getWidth()/2f, logo.getHeight()/2f);
        sb.end();

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
        sb.dispose();
        stage.dispose();
    }
}
