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

public class SinglePlayerView implements Screen {

    private SpriteBatch sb;
    private Stage stage;
    private SinglePlayerController singlePlayerController;
    private AssetsController assetsController;

    private Sprite backdrop;
    private Label numberOfShots;
    private ScreenController screenController;
    float fontScaler;

    public SinglePlayerView(ScreenController screenController, SinglePlayerController singlePlayerController) {
        this.singlePlayerController = singlePlayerController;
        fontScaler = Gdx.graphics.getHeight() * (3f / 1000f);
        Engine.getInstance().initializeEngine(0);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.assetsController = AssetsController.getInstance();

        numberOfShots = new Label("Shots: " + singlePlayerController.getNumberOfShots(), assetsController.getSkin());
        this.screenController = screenController;
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
        // stage.act(Math.min(delta, 1 / 30f));
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
