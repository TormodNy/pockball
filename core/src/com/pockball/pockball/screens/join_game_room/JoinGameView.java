package com.pockball.pockball.screens.join_game_room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;

public class JoinGameView extends ScreenView {
    private final JoinGameController controller;
    private Label errorLabel;

    public JoinGameView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);

        this.controller = JoinGameController.getInstance();
    }

    @Override
    public void show() {
        // Join game table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Back button
        TextButton backButton = new TextButton(" Back ", assetsController.getSkin());
        backButton.getLabel().setFontScale(assetScaler);
        backButton.setHeight(assetScaler*120);
        backButton.setWidth(assetScaler*300);
        backButton.pad(assetScaler*20);
        stage.addActor(backButton);

        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                screenController.changeScreen(ScreenModel.Screen.MAINMENU, ScreenModel.Screen.JOIN_GAME);
                return true;
            }
        });

        Label title = new Label(" Join game ",  assetsController.getSkin());
        title.setFontScale(assetScaler * 2);
        table.add(title);
        table.row();
        table.setColor(Color.BLUE);

        Label textFieldLabel = new Label("Enter room code:",  assetsController.getSkin());
        textFieldLabel.setFontScale(assetScaler);
        table.add(textFieldLabel);
        table.row();

        TextField roomCodeField = new TextField("", assetsController.getSkin());
        roomCodeField.setAlignment(Align.center);
        table.add(roomCodeField).width(400*assetScaler);
        table.row();

        errorLabel = new Label("",  assetsController.getSkin());
        errorLabel.setFontScale(assetScaler);
        errorLabel.setColor(Color.RED);
        table.add(errorLabel);
        table.row();

        TextButton joinGameButton = new TextButton(" Join game ", assetsController.getSkin());
        joinGameButton.getLabel().setFontScale(assetScaler);
        table.add(joinGameButton);

        joinGameButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (roomCodeField.getText() == null || roomCodeField.getText().equals("")) {
                    errorLabel.setText("Room code cannot be empty");
                    return true;
                }
                controller.getRoom(roomCodeField.getText());
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        errorLabel.setText(controller.getErrorMessage());

        stage.draw();
    }
}
