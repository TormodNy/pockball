package com.pockball.pockball.screens.gameover;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;

public class GameoverView extends ScreenView {

    public GameoverView(ScreenController screenController, ScreenModel.Screen screenModel) {
        super(screenController, screenModel);
    }

    @Override
    public void show(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameoverTitle = new Label("GAME OVER! ", assetsController.getSkin());
        gameoverTitle.setFontScale(assetScaler);
        table.add(gameoverTitle);

        TextButton quitButton = new TextButton("QUIT", assetsController.getSkin());
        quitButton.getLabel().setFontScale(assetScaler);

        table.row().padTop(50);
        table.add(quitButton).uniformX();

        Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.GAMEOVER);
    }
}

