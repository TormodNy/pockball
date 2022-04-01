package com.pockball.pockball.screens.create_game_room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.screens.ScreenController;

public class CreateGameRoomView implements Screen {
    private Stage stage;
    private CreateGameRoomController controller;

    public CreateGameRoomView(CreateGameRoomController controller) {
        this.controller = controller;
        this.controller.testDb();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
