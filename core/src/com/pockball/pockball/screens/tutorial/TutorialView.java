package com.pockball.pockball.screens.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

public class TutorialView extends ScreenView {
    private final SpriteBatch sb;
    private final Array<Texture> textures;
    private int currentPng;


    public TutorialView(ScreenController screenController, ScreenModel.Screen previousScreen) {
        super(screenController, previousScreen);
        this.sb = new SpriteBatch();

        this.setCurrentPng(0);

        textures = new Array<>();
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));
        textures.add(new Texture("tutorial/first.png"));
        Gdx.input.setInputProcessor(stage);
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
        header.setFontScale(assetsController.getAssetScaler());
        titleTable.add(header);
        titleTable.top();
        titleTable.row().padTop(50);


        TextButton lastButton = new TextButton(" LAST ", assetsController.getSkin());
        lastButton.getLabel().setFontScale(assetScaler);
        table.add(lastButton).align(Align.left);
        lastButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                decrementCurrentPng();
                header.setText(getHeaderText());
            }
        });

        table.bottom();
        TextButton quitButton = new TextButton(" MAIN MENU ", assetsController.getSkin());
        quitButton.getLabel().setFontScale(assetScaler);
        table.add(quitButton).align(Align.center).pad(50);

        TextButton nextButton = new TextButton(" NEXT ", assetsController.getSkin());
        nextButton.getLabel().setFontScale(assetScaler);
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
                (Gdx.graphics.getWidth()/2f) - (textures.get(currentPng).getWidth()*assetScaler/2f),
                (Gdx.graphics.getHeight()* (1f/5f)),
                textures.get(currentPng).getWidth()*assetScaler,
                textures.get(currentPng).getHeight()*assetScaler);
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
        return  "TUTORIAL: " + (currentPng+1) + " of " + textures.size;
    }

}


