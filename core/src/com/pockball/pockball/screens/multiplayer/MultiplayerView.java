package com.pockball.pockball.screens.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

public class MultiplayerView extends ScreenView {
    private final MultiplayerController controller;

    private final Label timerLabel;
    private final Label myBallType;
    private final Label waitingForOtherPlayerLabel;
    private boolean showWaitingForOtherPlayer = true;

    private final float buttonScaler;

    public MultiplayerView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);

        this.controller = MultiplayerController.getInstance();
        Engine.getInstance().initializeEngine(0);

        buttonScaler = assetScaler * 0.65f;


        timerLabel = new Label("60", assetsController.getSkin());
        timerLabel.setFontScale(buttonScaler);

        myBallType = new Label("Type", assetsController.getSkin());
        myBallType.setFontScale(buttonScaler);

        waitingForOtherPlayerLabel = new Label("Waiting for other player...", assetsController.getSkin());
        waitingForOtherPlayerLabel.setFontScale(buttonScaler);
        Context.getInstance().getState().setIdle(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        myBallType.setPosition(300 * assetScaler, 980 * assetScaler);
        stage.addActor(myBallType);

        timerLabel.setPosition(1500 * assetScaler, 980*assetScaler);
        stage.addActor(timerLabel);

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
        pauseButton.getLabel().setFontScale(buttonScaler);
        tablePause.top().right();
        tablePause.padTop(5);
        tablePause.padRight(5);
        tablePause.add(pauseButton);


        Util.addPathToButton(screenController,
                            pauseButton,
                            ScreenModel.Screen.SETTINGS,
                            ScreenModel.Screen.MULTIPLAYER);

    }


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

        timerLabel.setText(controller.updateTimerString(delta));

        myBallType.setText(controller.getMyBallType());

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
