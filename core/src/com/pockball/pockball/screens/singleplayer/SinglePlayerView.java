package com.pockball.pockball.screens.singleplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pockball.pockball.ecs.Engine;

public class SinglePlayerView implements Screen {
    private Stage stage;
    private SinglePlayerController singlePlayerController;

    public SinglePlayerView(SinglePlayerController singlePlayerController) {
        this.singlePlayerController = singlePlayerController;
        Engine.getInstance().initializeEngine();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Engine.getInstance().update(delta);
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

    }
}
