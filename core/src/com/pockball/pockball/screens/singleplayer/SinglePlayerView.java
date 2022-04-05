package com.pockball.pockball.screens.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.game_states.State;

public class SinglePlayerView extends ScreenView {

    private final SinglePlayerController singlePlayerController;
    private final Label numberOfShots;

    private final float buttonScaler;

    public SinglePlayerView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);

        this.singlePlayerController = SinglePlayerController.getInstance();
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

        // TODO: Rename
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton powerupsButton = new TextButton("Powerups", assetsController.getSkin());
        powerupsButton.getLabel().setFontScale(buttonScaler);
        table.add(powerupsButton);
        table.top().left();
        table.pad(4);

        powerupsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Show powerups
                singlePlayerController.setShowPowerups(!singlePlayerController.getShowPowerups());
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
