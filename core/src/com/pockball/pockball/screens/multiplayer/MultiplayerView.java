package com.pockball.pockball.screens.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;

public class MultiplayerView implements Screen {
    private final MultiplayerController controller;
    private final AssetsController assetsController;
    private final ScreenController screenController;

    private final float assetScaler;

    private final Stage stage;
    private final Label waitingForOtherPlayerLabel;
    private boolean showWaitingForOtherPlayer = true;

    public MultiplayerView(ScreenController screenController, MultiplayerController controller) {
        this.controller = controller;
        Engine.getInstance().initializeEngine(0);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.screenController = screenController;
        this.assetsController = AssetsController.getInstance();
        assetScaler = assetsController.getAssetScaler() * 0.65f;

        waitingForOtherPlayerLabel = new Label("Waiting for other player...", assetsController.getSkin());
        waitingForOtherPlayerLabel.setFontScale(assetScaler);
        Context.getInstance().getState().setIdle(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table tableScore = new Table();
        tableScore.setFillParent(true);
        stage.addActor(tableScore);
        tableScore.add(waitingForOtherPlayerLabel);
        tableScore.top();
        tableScore.padTop(10);

        Table tablePause = new Table();
        tablePause.setFillParent(true);
        stage.addActor(tablePause);
        TextButton pauseButton = new TextButton("Pause", assetsController.getSkin());
        pauseButton.getLabel().setFontScale(assetScaler);
        tablePause.top().right();
        tablePause.padTop(5);
        tablePause.padRight(5);
        tablePause.add(pauseButton);
        Util.addPathToButton(screenController, pauseButton, ScreenModel.Screen.SETTINGS,
                ScreenModel.Screen.SINGLEPLAYER);

        // TODO: Rename
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton powerupsButton = new TextButton("Powerups", assetsController.getSkin());
        powerupsButton.getLabel().setFontScale(assetScaler);
        table.add(powerupsButton);
        table.top().left();
        table.pad(4);

        powerupsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Show powerups
                controller.setShowPowerups(!controller.getShowPowerups());
            }
        });

    }

    ;

    // Call this method to toggle the text
    private void toggleWaitingForOtherPlayer() {
        showWaitingForOtherPlayer = !showWaitingForOtherPlayer;
    }

    @Override
    public void render(float delta) {
        Engine.getInstance().update(delta);

        // TODO: Performance optimization
        // TODO: If stateChanged
        if (showWaitingForOtherPlayer) {
            waitingForOtherPlayerLabel.setText(controller.getCurrentStateString());
        } else {
            waitingForOtherPlayerLabel.setText(controller.getCurrentStateString());
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        // stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
        ;
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
