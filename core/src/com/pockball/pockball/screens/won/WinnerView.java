package com.pockball.pockball.screens.won;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;

public class WinnerView extends ScreenView {

    public WinnerView(ScreenController screenController, ScreenModel.Screen screenModel) {
        super(screenController, screenModel);
    }

    @Override
    public void show(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameoverTitle = new Label("YOU WON! ", assetsController.getSkin());
        gameoverTitle.setFontScale(assetScaler);
        table.add(gameoverTitle);
        table.row().padTop(50);

        Label winnerScore = new Label("YOUR SCORE: " + SinglePlayerController.getInstance().getNumberOfShots(), assetsController.getSkin());
        winnerScore.setFontScale(assetScaler);
        table.add(winnerScore);

        TextButton quitButton = new TextButton("QUIT", assetsController.getSkin());
        quitButton.getLabel().setFontScale(assetScaler);
        table.row().padTop(50);
        table.add(quitButton).uniformX();

        Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.GAMEOVER);
    }
}

