package com.pockball.pockball.screens.join_game_room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;

public class JoinGameView implements Screen {
    private final JoinGameController controller;
    private final AssetsController assetsController;
    private final ScreenController screenController;

    private final Stage stage;
    private Label errorLabel;

    private final float assetScaler;

    public JoinGameView(JoinGameController controller) {
        this.controller = controller;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.screenController = ScreenController.getInstance();
        this.assetsController = AssetsController.getInstance();
        this.assetScaler = assetsController.getAssetScaler();
    }

    @Override
    public void show() {
        // Join game table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Back button
        TextButton backButton = new TextButton("Back", assetsController.getSkin());
        backButton.getLabel().setFontScale(assetScaler);
        stage.addActor(backButton);

        backButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                screenController.changeScreen(ScreenModel.Screen.MAINMENU, ScreenModel.Screen.JOIN_GAME);
                return true;
            }
        });

        Label title = new Label("Join game",  assetsController.getSkin());
        title.setFontScale(assetScaler * 2);
        table.add(title);
        table.row();
        table.setColor(Color.BLUE);

        Label textFieldLabel = new Label("Enter room code:",  assetsController.getSkin());
        textFieldLabel.setFontScale(assetScaler);
        table.add(textFieldLabel);
        table.row();

        TextField roomCodeField = new TextField("", assetsController.getSkin());
        table.add(roomCodeField);
        table.row();

        errorLabel = new Label("",  assetsController.getSkin());
        errorLabel.setFontScale(assetScaler);
        errorLabel.setColor(Color.RED);
        table.add(errorLabel);
        table.row();

        TextButton joinGameButton = new TextButton("Join game", assetsController.getSkin());
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

        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
