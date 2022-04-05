package com.pockball.pockball.screens.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;

public class MainMenuView implements Screen {

    private final SpriteBatch sb;
    private final Stage stage;
    private final AssetsController assetsController;
    private final ScreenController screenController;
    private final float assetScaler;

    private final Texture logo;


    public MainMenuView(ScreenController screenController) {
        this.screenController = screenController;
        this.sb = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.assetsController = AssetsController.getInstance();

        assetScaler = assetsController.getAssetScaler();
        this.logo = new Texture("pockballLogo.png");
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton singleplayerButton = new TextButton("Singleplayer", assetsController.getSkin());
        TextButton joinGameButton = new TextButton("Join game", assetsController.getSkin());
        TextButton createGameButton = new TextButton("Create game", assetsController.getSkin());
        TextButton settingsButton = new TextButton("Settings", assetsController.getSkin());

        singleplayerButton.getLabel().setFontScale(assetScaler);
        joinGameButton.getLabel().setFontScale(assetScaler);
        createGameButton.getLabel().setFontScale(assetScaler);
        settingsButton.getLabel().setFontScale(assetScaler);

        table.row().padTop(200);
        table.add(singleplayerButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(createGameButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(joinGameButton).uniformX();
        table.row().pad(20, 0, 0, 0);
        table.add(settingsButton).uniformX();

        Util.addPathToButton(screenController, singleplayerButton, ScreenModel.Screen.SINGLEPLAYER, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, settingsButton, ScreenModel.Screen.SETTINGS, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, joinGameButton, ScreenModel.Screen.JOIN_GAME, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, createGameButton, ScreenModel.Screen.CREATE_GAME, ScreenModel.Screen.MAINMENU);
    }


    @Override
    public void render(float delta) {
        sb.begin();
        float RGBDivider = 256f;
        Gdx.gl.glClearColor(11f / RGBDivider, 137f / RGBDivider, 1f / RGBDivider, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(logo,
                (Gdx.graphics.getWidth() / 2f) - (logo.getWidth() * assetScaler / 2f),
                (Gdx.graphics.getHeight() * (2f / 3f)),
                logo.getWidth() * assetScaler,
                logo.getHeight() * assetScaler);
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
