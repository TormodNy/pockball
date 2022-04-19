package com.pockball.pockball.screens.won;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;
import com.pockball.pockball.screens.ScreenView;
import com.pockball.pockball.screens.Util;
import com.pockball.pockball.screens.multiplayer.MultiplayerController;
import com.pockball.pockball.screens.singleplayer.SinglePlayerController;

public class WinnerView extends ScreenView {

    boolean multiplayer;

    public WinnerView(ScreenController screenController, ScreenModel.Screen screenModel, boolean multiplayer) {
        super(screenController, screenModel);
        this.multiplayer = multiplayer;
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

        Label winnerScore;

        if (multiplayer){
            winnerScore = new Label("YOUR SCORE: " + MultiplayerController.getInstance().getMyScore(), assetsController.getSkin());
        } else {
            winnerScore = new Label("YOUR SCORE: " + SinglePlayerController.getInstance().getNumberOfShots(), assetsController.getSkin());
        }

        winnerScore.setFontScale(assetScaler);
        table.add(winnerScore);

        TextButton quitButton = new TextButton(" QUIT ", assetsController.getSkin());
        quitButton.getLabel().setFontScale(assetScaler);
        table.row().padTop(50);
        table.add(quitButton).uniformX();

        Util.addPathToButton(screenController, quitButton, ScreenModel.Screen.MAINMENU, ScreenModel.Screen.GAMEOVER);
    }
}

