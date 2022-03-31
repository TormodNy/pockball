package com.pockball.pockball.screens.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;

public class MultiplayerView implements Screen {
    private Stage stage;
    private MultiplayerController controller;
    float fontScaler;


    public MultiplayerView(MultiplayerController controller) {
        this.controller = controller;
        Engine.getInstance().initializeEngine(0);
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
