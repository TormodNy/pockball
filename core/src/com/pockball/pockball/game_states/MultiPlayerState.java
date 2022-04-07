package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.db_models.PlaceBallEvent;
import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.db_models.ShotEvent;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.db_models.BallTypeModel;
import com.pockball.pockball.firebase.FirebaseController;
import com.pockball.pockball.screens.multiplayer.MultiplayerController;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerState implements State {

    private final Entity myPlayerEntity, opponentPlayerEntity;
    private boolean ballTypeSet = false;
    private float gameVolume;

    private RoomModel roomModel;
    private FirebaseController firebaseController;
    private String myKey, opponentKey;

    // Is this player host
    private boolean isHost, myTurn, whiteBallFellDownThisRound, meIdle, opponentIdle, firstBall, putOpponent;
    private BallType myBallType, opponentBallType;

    private int lastEventHandled = -1;
    private float timer = 60f;


    public MultiPlayerState(RoomModel roomModel, boolean isHost) {
        this.roomModel = roomModel;
        this.isHost = isHost;
        this.whiteBallFellDownThisRound = false;

        this.myTurn = this.isHost && roomModel.hostTurn;

        this.firstBall = true;
        this.putOpponent = false;

        if (isHost) {
            // Who am I
            myKey = "host";

            // Who are the opponent
            opponentKey = "client";
        } else {
            // Who am I
            myKey = "client";

            // Who are the opponent
            opponentKey = "host";
        }


        firebaseController = FirebaseController.getInstance();

        if (!this.isHost) {
            roomModel.client = new PlayerModel("client");
            firebaseController.writeToDb(roomModel.roomId + ".client", roomModel.client);
        }

        // Listeners
        firebaseController.listenToPlayerEvents(roomModel.roomId + "." + opponentKey + ".events");
        firebaseController.listenToOpponentIdleState(roomModel.roomId + "." + opponentKey + ".idle");
        firebaseController.listenToBallType(roomModel.roomId);
        firebaseController.listenToHostTurn(roomModel.roomId);

        // Create player entities
        myPlayerEntity = EntityFactory.getInstance().createPlayer("player1");
        opponentPlayerEntity = EntityFactory.getInstance().createPlayer("player2");
    }

    @Override
    public void ballIntoHole(BallType ballType, int holeID) {
        switch (ballType) {
            case WHITE:
                whiteBallFellDownThisRound = true;
                break;
            case BLACK:
                //TODO: implement bb logic
                boolean lost;
                if (firstBall){
                    if (!myTurn){
                        //i put black ball
                        lost = getInActivePlayerModel().score.size() != 7;
                    } else {
                        //opponent put black ball
                        lost = getInActivePlayerModel().score.size() == 7;
                    }
                }
                else {
                    //player put two balls, one of player and black ball
                    // or
                    // opponent put two balls, one of player and black
                    if (!putOpponent){ // have put own type then black
                        if (myTurn){
                            lost = getActivePlayerModel().score.size() != 7;
                        } else {
                            lost = getActivePlayerModel().score.size() == 7;
                        }

                    } else { // have hit opponents type then black
                        if (myTurn) {
                            lost = getInActivePlayerModel().score.size() == 7;
                        } else {
                            lost = getInActivePlayerModel().score.size() != 7;
                        }
                    }
                }


                if (lost){ //Lost
                    MultiplayerController.getInstance().gameLost();
                } else { //Won
                    MultiplayerController.getInstance().gameWon();

            }
                break;
            default:
                // Set ball type
                if (!ballTypeSet) {
                    if (!myTurn){ // turn is switched at shoot
                        setMyBallType(ballType);

                    } else {
                        if (ballType.equals(BallType.STRIPED)){
                            setMyBallType(BallType.SOLID);
                        } else
                        setMyBallType(BallType.STRIPED);

                    }
                }

                if (getCurrentPlayerBallType() == null) {
                    break;
                }
                if (firstBall){
                    firstBall = false;
                    if (getNotCurrentPlayerBallType().equals(ballType)){
                        getInActivePlayerModel().score.add(ballType.toString());
                        if (!myTurn && !whiteBallFellDownThisRound) setNextPlayerTurn(true);
                    }
                    else {
                        getActivePlayerModel().score.add(ballType.toString());
                        putOpponent = true;
                    }
                    if(getCurrentPlayerBallType() != null){
                        System.out.println("\n score of : " + getNotCurrentPlayerBallType().toString() + ": " + getInActivePlayerModel().score.size());
                        System.out.println("\n score of : " + getCurrentPlayerBallType().toString() + ": " + getActivePlayerModel().score.size());
                    }
                    break;
                }
                // TODO: THIS DOES NOT WORK
                if (getCurrentPlayerBallType().equals(ballType) && !firstBall) {
                    getActivePlayerModel().score.add(ballType.toString());
                    if (!myTurn && !whiteBallFellDownThisRound && !putOpponent) setNextPlayerTurn(true);
                } else {
                    getInActivePlayerModel().score.add(ballType.toString());
                    putOpponent = true;
                }
                if(getCurrentPlayerBallType() != null){
                    System.out.println("\n score of : " + getNotCurrentPlayerBallType().toString() + ": " + getInActivePlayerModel().score.size());
                    System.out.println("\n score of : " + getCurrentPlayerBallType().toString() + ": " + getActivePlayerModel().score.size());
                }
        }
    }

    public int getTimerInt(){
        return (int) timer;
    }
    public float getTimer(){
        return timer;
    }
    public void updateTimer(float delta){
        if(!getIdle()){
            timer = 60;
            return;
        }
        timer -= delta;
    }

    private BallType getCurrentPlayerBallType() {
        return myTurn ? myBallType : opponentBallType;
    }
    private BallType getNotCurrentPlayerBallType(){
        return myTurn ? opponentBallType : myBallType;
    }

    private PlayerModel getActivePlayerModel() {
        return isHost ? (myTurn ? roomModel.host : roomModel.client) : (myTurn ? roomModel.client : roomModel.host);
    }
    private PlayerModel getInActivePlayerModel(){
        return  isHost ? (myTurn ? roomModel.client : roomModel.host) : (myTurn ? roomModel.host : roomModel.client);
    }

    public BallType getMyBallType () {
        return myBallType;
    }

    @Override
    public void changeGameVolume(float gameVolume) {
        this.gameVolume = gameVolume;
    }

    @Override
    public float getGameVolume() {
        return gameVolume;
    }

    private void setNextPlayerTurn(boolean myTurn) {
        whiteBallFellDownThisRound = false; //TODO: What if white ball falls in after?
        roomModel.hostTurn = isHost == myTurn;
        firebaseController.writeToDb(roomModel.roomId + ".hostTurn", roomModel.hostTurn);
    }

    private void setNextPlayerTurn() {
        setNextPlayerTurn(!myTurn);
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[2];
        players[0] = myPlayerEntity;
        players[1] = opponentPlayerEntity;
        return players;
    }

    @Override
    public void addEvent(EventModel event) {
        PlayerModel myPlayer = (isHost ? roomModel.client : roomModel.host);
        if (myPlayer.events == null) myPlayer.events = new ArrayList<>();
        myPlayer.events.add(event);

        firebaseController.writeToDb(
                roomModel.roomId + "." + myKey + ".events",
                (isHost ? roomModel.client : roomModel.host).events
        );

        // Switch player if it is a shot event
        if (event.type == EventModel.Type.SHOT) {
            setNextPlayerTurn();
            firstBall = true;
            putOpponent = false;
        }
    }

    @Override
    public void fireOpponentEventChange(List<EventModel> eventModelList) {
        if (eventModelList.size() == 0) return;

        if (lastEventHandled == eventModelList.size() - 1) {
            // Event is already handled
            return;
        }

        lastEventHandled = eventModelList.size() - 1;
        EventModel event = eventModelList.get(lastEventHandled);

        opponentIdle = false;

        switch (event.type) {
            case SHOT:
                ShotEvent shot = (ShotEvent) event;
                firstBall = true;
                putOpponent = false;
                Vector2 force = new Vector2(shot.x, shot.y);
                Engine.getInstance().shootBallWithForce(force, false);
                break;
            case PLACE_BALL:
                PlaceBallEvent placeBall = (PlaceBallEvent) event;
                Vector2 position = new Vector2(placeBall.x, placeBall.y);
                Engine.getInstance().placeWhiteBall(position, false);


        }
    }

    @Override
    public void fireOpponentIsIdle() {
        this.opponentIdle = true;
        updateMyTurn();
    }

    @Override
    public void fireBallTypeSet(BallTypeModel ballType) {
        if (isHost) {
            myBallType = ballType.host;
            this.opponentBallType = ballType.client;
        } else {
            myBallType = ballType.client;
            this.opponentBallType = ballType.host;
        }
        ballTypeSet = true;
        firebaseController.stopListenToBallType();
    }

    @Override
    public void fireHostTurn(boolean hostTurn) {
        roomModel.hostTurn = hostTurn;
        updateMyTurn();
    }

    @Override
    public void setIdle(boolean idle) {
        this.meIdle = idle;
        firebaseController.writeToDb(roomModel.roomId + "." + myKey + ".idle", this.meIdle);
        updateMyTurn();
    }

    @Override
    public boolean getIdle() {
        return meIdle && opponentIdle;
    }

    @Override
    public boolean canPerformAction() {
        return myTurn && getIdle();
    }

    public void setMyBallType(BallType ballType) {
        myBallType = ballType;
        opponentBallType = myBallType == BallType.STRIPED ? BallType.SOLID : BallType.STRIPED;

        BallTypeModel ballTypeModel;
        if (isHost) {
            ballTypeModel = new BallTypeModel(myBallType, opponentBallType);
        } else {
            ballTypeModel = new BallTypeModel(opponentBallType, myBallType);
        }


        firebaseController.stopListenToBallType();
        firebaseController.writeToDb(roomModel.roomId + ".ballTypes", ballTypeModel);

        ballTypeSet = true;
    }

    public void removeRoom () {
        firebaseController.removeFromDb(roomModel.roomId);
    }

    public int getNumberOfShots() {
        return -1;
    }

    @Override
    public void incNumberOfShots() {
    }

    @Override
    public void reset() {
        firebaseController.stopListenToEventsChanges();
        firebaseController.stopListenToOpponentIdleState();
        firebaseController.stopListenToBallType();
        firebaseController.stopListenToHostTurn();
    }

    public void updateMyTurn() {
        myTurn = isHost == roomModel.hostTurn;
    }

    @Override
    public boolean getIsMyTurn() {
        return myTurn;
    }
}
