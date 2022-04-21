package com.pockball.pockball.screens.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.SinglePlayerState;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

public class SinglePlayerView extends ScreenView {

    private final SinglePlayerController singlePlayerController;
    private final Label numberOfShots;

    private final float buttonScaler;

    public SinglePlayerView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);

        // Set new single player state
        Context.getInstance().setState(new SinglePlayerState());

        this.singlePlayerController = SinglePlayerController.getInstance();
        singlePlayerController.reset();
        Engine.getInstance().initializeEngine(0);

        buttonScaler = assetScaler * 0.65f;

        numberOfShots = new Label("Shots: " + singlePlayerController.getNumberOfShots(), assetsController.getSkin());
        numberOfShots.setFontScale(buttonScaler);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table tableScore = new Table();
        tableScore.setFillParent(true);
        stage.addActor(tableScore);
        tableScore.add(numberOfShots);
        tableScore.top();
        tableScore.padTop(5);

        Table tablePause = new Table();
        tablePause.setFillParent(true);
        stage.addActor(tablePause);
        TextButton pauseButton = new TextButton("Pause", assetsController.getSkin());
        pauseButton.getLabel().setFontScale(buttonScaler);
        tablePause.top().right();
        tablePause.padTop(5);
        tablePause.padRight(5);
        tablePause.add(pauseButton);
        Util.addPathToButton(screenController, pauseButton, ScreenModel.Screen.SETTINGS,
                ScreenModel.Screen.SINGLEPLAYER);

        Table powerupTable = new Table();
        powerupTable.setFillParent(true);
        stage.addActor(powerupTable);

        TextButton powerupsButton = new TextButton("Powerups", assetsController.getSkin());
        powerupsButton.getLabel().setFontScale(buttonScaler);
        powerupTable.add(powerupsButton);
        powerupTable.top().left();
        powerupTable.pad(4);

        powerupsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Show powerups
                if (!Context.getInstance().getState().hasAimed()) {
                    singlePlayerController.setShowPowerups(!singlePlayerController.getShowPowerups());
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Engine.getInstance().update(delta);

        numberOfShots.setText("Shots: " + singlePlayerController.getNumberOfShots());

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
}
