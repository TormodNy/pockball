package com.pockball.pockball.screens.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

public class MainMenuView extends ScreenView {

    private final SpriteBatch sb;
    private final Texture logo;


    public MainMenuView(ScreenController screenController) {
        super(screenController, null);

        this.sb = new SpriteBatch();
        this.logo = new Texture("pockballLogo.png");
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton singleplayerButton = new TextButton(" Singleplayer ", assetsController.getSkin());
        TextButton joinGameButton = new TextButton(" Join game ", assetsController.getSkin());
        TextButton createGameButton = new TextButton(" Create game ", assetsController.getSkin());
        TextButton settingsButton = new TextButton(" Settings ", assetsController.getSkin());
        TextButton tutorialButton = new TextButton(" Tutorial ", assetsController.getSkin());



        singleplayerButton.getLabel().setFontScale(assetScaler);
        joinGameButton.getLabel().setFontScale(assetScaler);
        createGameButton.getLabel().setFontScale(assetScaler);
        settingsButton.getLabel().setFontScale(assetScaler);
        tutorialButton.getLabel().setFontScale(assetScaler);


        table.row().padTop(assetScaler*200);
        table.add(singleplayerButton);
        table.row().pad(assetScaler*10);
        table.add(createGameButton);
        table.row().pad(assetScaler*10);
        table.add(joinGameButton);
        table.row().pad(assetScaler*10);
        table.add(settingsButton);
        table.row().pad(assetScaler*10);
        table.add(tutorialButton);

        Util.addPathToButton(screenController, singleplayerButton, ScreenModel.Screen.SINGLEPLAYER, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, settingsButton, ScreenModel.Screen.SETTINGS, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, joinGameButton, ScreenModel.Screen.JOIN_GAME, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, createGameButton, ScreenModel.Screen.CREATE_GAME, ScreenModel.Screen.MAINMENU);
        Util.addPathToButton(screenController, tutorialButton, ScreenModel.Screen.TUTORIAL, ScreenModel.Screen.MAINMENU);
    }


    @Override
    public void render(float delta) {
        sb.begin();
        float RGBDivider = 256f;
        Gdx.gl.glClearColor(11f / RGBDivider, 137f / RGBDivider, 1f / RGBDivider, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(logo,
                (Gdx.graphics.getWidth() / 2f) - (logo.getWidth() * assetScaler / 2f),
                (Gdx.graphics.getHeight() * (2.2f / 3f)),
                logo.getWidth() * assetScaler,
                logo.getHeight() * assetScaler);
        sb.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();
    }
}
