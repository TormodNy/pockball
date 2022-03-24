package com.pockball.pockball.screens.create_game_room;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pockball.pockball.ecs.Engine;

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
