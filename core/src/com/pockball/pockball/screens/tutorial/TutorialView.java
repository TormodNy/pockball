package com.pockball.pockball.screens.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pockball.pockball.assets.AssetsController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.Util;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;

public class TutorialView implements Screen {
    private SpriteBatch sb;
    private Stage stage;
    private AssetsController assetsController;
    private ScreenController screenController;
    private ScreenModel.Screen screenModel;
    private final float fontScaler;
    private final float pngScaler;
    private Array<Texture> textures;
    private int currentPng;


    public TutorialView(ScreenController screenController, ScreenModel.Screen screenModel) {
        this.screenController = screenController;
        this.sb = new SpriteBatch();

        fontScaler = Gdx.graphics.getHeight()*(3f/1000f);
        pngScaler = Gdx.graphics.getHeight()*(1f/1000f);
        this.setCurrentPng(0);

        textures = new Array<>();
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));


        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.assetsController = AssetsController.getInstance();
        this.screenModel = screenModel;
    }

    @Override
    public void show(){
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        stage.addActor(titleTable);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label header = new Label(getHeaderText(), assetsController.getSkin());
        header.setFontScale(fontScaler);
        titleTable.add(header);
        titleTable.top();
        titleTable.row().padTop(50);


        TextButton lastButton = new TextButton("LAST", assetsController.getSkin());
        lastButton.getLabel().setFontScale(fontScaler);
        table.add(lastButton).align(Align.left);
        lastButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                decrementCurrentPng();
                header.setText(getHeaderText());
            }
        });

        table.bottom();
        TextButton quitButton = new TextButton("MAIN MENU", assetsController.getSkin());
        quitButton.getLabel().setFontScale(fontScaler);
        table.add(quitButton).align(Align.center);

        TextButton nextButton = new TextButton("NEXT", assetsController.getSkin());
        nextButton.getLabel().setFontScale(fontScaler);
        table.add(nextButton).align(Align.right);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                incrementCurrentPng();
                header.setText(getHeaderText());
            }
        });

        Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.TUTORIAL);
    }

    @Override
    public void render(float delta) {
        sb.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(textures.get(getCurrentPng()),
                (Gdx.graphics.getWidth()/2f) - (textures.get(currentPng).getWidth()*pngScaler/2f),
                (Gdx.graphics.getHeight()* (1f/5f)),
                textures.get(currentPng).getWidth()*pngScaler,
                textures.get(currentPng).getHeight()*pngScaler);
        sb.end();

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
        stage.dispose();
    }

    public int getCurrentPng() {
        return currentPng;
    }

    public void setCurrentPng(int currentPng) {
        System.out.println(currentPng);
        this.currentPng = currentPng;
    }

    public void incrementCurrentPng(){
        if(textures.size -1 <= getCurrentPng()){
            setCurrentPng(textures.size - 1);
        } else {
            setCurrentPng(getCurrentPng() + 1);
        }
    }

    public void decrementCurrentPng(){
        if (getCurrentPng() <= 0){
            setCurrentPng(0);
        } else {
            setCurrentPng(getCurrentPng() - 1);
        }
    }
    public String getHeaderText(){
        return  "TUTORIAL: " + currentPng + " of " + textures.size;
    }

}


