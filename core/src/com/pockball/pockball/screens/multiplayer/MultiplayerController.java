package com.pockball.pockball.screens.multiplayer;

import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.game_modes.MultiPlayerGameMode;
import com.pockball.pockball.game_modes.GameMode;
import com.pockball.pockball.screens.GameController;
import com.pockball.pockball.screens.ScreenController;
import com.pockball.pockball.screens.ScreenModel;


public class MultiplayerController extends GameController {
    private static MultiplayerController multiplayerControllerInstance = null;

    private MultiplayerController() {
    }

    public static MultiplayerController getInstance() {
        if (multiplayerControllerInstance == null) {
            multiplayerControllerInstance = new MultiplayerController();
        }

        currentController = multiplayerControllerInstance;
        return multiplayerControllerInstance;
    }

    public String getMyBallType () {
        MultiPlayerGameMode state = (MultiPlayerGameMode) GameModeContext.getInstance().getState();

        if (state.getMyBallType() != null) {
            return state.getMyBallType().toString();
        }

        return "Type";
    }

    public void removeRoom () {
        MultiPlayerGameMode state = (MultiPlayerGameMode) GameModeContext.getInstance().getState();
        state.removeRoom();
    }

    public String updateTimerString(float delta) {
        MultiPlayerGameMode state = (MultiPlayerGameMode) GameModeContext.getInstance().getState();
        state.updateTimer(delta);
        boolean myTurn = state.getIsMyTurn();
        int timer = state.getTimerInt();
        if (timer >= 0){
            return Integer.toString(timer);
        } else{
            if (timer <= -3){
                if (myTurn){
                    gameLost();
                } else{
                    gameWon();
                }
            }
            return Integer.toString(0);

        }
    }
    public void gameOver(){
        MultiPlayerGameMode state = (MultiPlayerGameMode) GameModeContext.getInstance().getState();
        boolean myTurn = state.getIsMyTurn();
        state.reset();
        if (myTurn){
            removeRoom();
        }
    }

    public int getMyScore(){
        MultiPlayerGameMode state = (MultiPlayerGameMode) GameModeContext.getInstance().getState();
        int score;
        if (state.getIsMyTurn()){
            score = state.getActivePlayerModel().score.size();
        } else {
            score = state.getInActivePlayerModel().score.size();
        }
        return score;
    }

    public void gameLost(){
        gameOver();
        ScreenController.getInstance().changeScreen(ScreenModel.Screen.GAMEOVER, ScreenModel.Screen.MULTIPLAYER);
    }
    public void gameWon(){
        gameOver();
        ScreenController.getInstance().changeScreen(ScreenModel.Screen.WINNER, ScreenModel.Screen.MULTIPLAYER);
    }


    @Override
    public String getCurrentStateString() {
        GameMode gameMode = GameModeContext.getInstance().getState();
        if (!gameMode.getIdle()) return "Waiting for balls to stop.";
        if (gameMode.canPerformAction()) return "Your turn. Make a shot!";
        if (!gameMode.getIsMyTurn()) return "Opponents turn";

        return "Unhandled state";
    }
}
