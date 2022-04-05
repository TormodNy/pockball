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
import com.pockball.pockball.screens.ScreenView;

public class CreateGameRoomView extends ScreenView {
    private final CreateGameRoomController controller;
    private Label title;

    public CreateGameRoomView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);
        this.controller = CreateGameRoomController.getInstance();
    }

    @Override
    public void show() {
        controller.createRoom();

        // Create game
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        title = new Label("Setting up room",  assetsController.getSkin());
        title.setFontScale(assetScaler * 2);
        title.setColor(Color.WHITE);
        table.add(title);
        table.row().padBottom(20);

        Label waitingLabel = new Label("Waiting for someone to join...", assetsController.getSkin());
        waitingLabel.setFontScale(assetScaler);
        table.add(waitingLabel);
        table.row().padBottom(10);

        TextButton backButton = new TextButton("Back", assetsController.getSkin());
        backButton.getLabel().setFontScale(assetScaler);
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

        if (!controller.getConfirmedRoomId().isEmpty()) {
            title.setText("Room code: " + controller.getConfirmedRoomId());
        }

        stage.draw();
    }
}
