package com.pockball.pockball.screens.create_game_room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class CreateGameRoomView implements Screen {
    private Stage stage;
    private CreateGameRoomController controller;
    private AssetsController assetsController;
    private Screen previousScreen;
    private ScreenController screenController;

    public CreateGameRoomView(CreateGameRoomController controller) {
        this.controller = controller;
        this.screenController = ScreenController.getInstance();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.assetsController = AssetsController.getInstance();
    }

    @Override
    public void show() {
        // Create game
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        String roomId = controller.createRoom();

        Label title = new Label("Room code: " + roomId,  assetsController.getSkin());
        title.setFontScale(2);
        title.setColor(Color.WHITE);
        table.add(title);
        table.row().padBottom(20);

        Label waitingLabel = new Label("Waiting for someone to join...", assetsController.getSkin());
        table.add(waitingLabel);
        table.row().padBottom(10);

        TextButton backButton = new TextButton("Back", assetsController.getSkin());
        table.add(backButton);
        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                controller.removeRoom();
                screenController.changeScreen(ScreenModel.Screen.MAINMENU, ScreenModel.Screen.CREATE_GAME);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
