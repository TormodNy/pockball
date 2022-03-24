package com.pockball.pockball.screens.join_game_room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.db_models.RoomModel;

import java.util.List;

public class JoinGameView implements Screen {
    private Stage stage;
    private JoinGameController controller;

    private AssetsController assetsController;

    private Table activeGameTable;

    public JoinGameView(JoinGameController controller) {
        this.controller = controller;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.assetsController = AssetsController.getInstance();
    }

    @Override
    public void show() {
        controller.listenForRooms();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Join a game: ",  assetsController.getSkin());
        table.add(title);
        table.row();
        table.setColor(Color.BLUE);

        activeGameTable = new Table();
        activeGameTable.setWidth(500);
        ScrollPane pane = new ScrollPane(activeGameTable, assetsController.getSkin());
        table.add(pane).size(600, 300);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        activeGameTable.clearChildren();

        List<RoomModel> rooms = controller.getAvailableRooms();
        if (rooms != null) {
            for (RoomModel room : controller.getAvailableRooms()) {
                Label roomLabel = new Label(room.roomId, assetsController.getSkin());
                TextButton joinButton = new TextButton("Join", assetsController.getSkin());
                activeGameTable.add(roomLabel).align(Align.left);
                joinButton.right();
                activeGameTable.add(joinButton);
                activeGameTable.row().pad(10);

                joinButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // Join a game
                        controller.joinGame(room.roomId);
                    }
                });
            }
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        controller.stopListenForRooms();
    }
}
