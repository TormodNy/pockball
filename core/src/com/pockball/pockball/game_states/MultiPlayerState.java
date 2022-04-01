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
import com.pockball.pockball.firebase.FirebaseController;

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
    private PlayerModel myPlayer, opponentPlayer;
    private boolean isHost;
    private boolean myTurn;
    private boolean meIdle;
    private boolean opponentIdle;
    private BallType myBallType, opponentBallType;


    public MultiPlayerState(RoomModel roomModel, boolean isHost) {
        this.roomModel = roomModel;
        this.isHost = isHost;

        this.myTurn = this.isHost && roomModel.hostTurn;

        if (isHost) {
            // Who am I
            myKey = "host";
            myPlayer = roomModel.host;

            // Who are the opponent
            opponentKey = "client";
            opponentPlayer = roomModel.client;
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

        // Create player entities
        myPlayerEntity = EntityFactory.getInstance().createPlayer("player1");
        opponentPlayerEntity = EntityFactory.getInstance().createPlayer("player2");
    }

    @Override
    public void ballIntoHole(BallType ballType, int holeID) {
        switch (ballType) {
            case WHITE:
                setNextPlayerTurn();
                break;
            case BLACK:
                // TODO: Implement logic
                break;
            default:
                // Set ball type
                if (ballTypeSet) {
                    ballTypeSet = true;
                    setMyBallType(ballType);
                }

                if (getCurrentPlayerBallType() == null) break;

                if (getCurrentPlayerBallType().equals(ballType)) {
                    getActivePlayerModel().score.add(ballType.toString());
                    setNextPlayerTurn(true);
                }
        }
    }

    private BallType getCurrentPlayerBallType() {
        return myTurn ? myBallType : opponentBallType;
    }

    private PlayerModel getActivePlayerModel() {
        return isHost ? (myTurn ? roomModel.host : roomModel.client) : (myTurn ? roomModel.client : roomModel.host);
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
        roomModel.hostTurn = isHost == myTurn;
        firebaseController.writeToDb(roomModel.roomId + ".hostTurn", roomModel.hostTurn);
    }

    private void setNextPlayerTurn() {
        setNextPlayerTurn(!roomModel.hostTurn);
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

        setNextPlayerTurn();
    }

    @Override
    public void fireOpponentEventChange(List<EventModel> eventModelList) {
        System.out.println("fireOpponentShotsChange() -> " + eventModelList);

        if (eventModelList.size() == 0) return;

        EventModel event = eventModelList.get(eventModelList.size() - 1);

        opponentIdle = false;

        switch (event.type) {
            case SHOT:
                ShotEvent shot = (ShotEvent) event;
                Vector2 force = new Vector2(shot.x, shot.y);
                System.out.println("force" + force);
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
        System.out.println("Opponent is idle. Can perform: " + canPerformAction());
    }

    @Override
    public void fireBallTypeSet(BallType hostBallType, BallType opponentBallType) {
        System.out.println("fireBallTypeSet() -> ");
        if (isHost) {
            myBallType = hostBallType;
            this.opponentBallType = opponentBallType;
        } else {
            this.opponentBallType = hostBallType;
            myBallType = hostBallType;
        }
        ballTypeSet = false;
        firebaseController.stopListenToBallType();
    }

    @Override
    public void setHostTurn(boolean hostTurn) {
        System.out.println("setHostTurn(" +hostTurn +")");
        roomModel.hostTurn = hostTurn;
        updateMyTurn();
    }

    @Override
    public void setIdle(boolean idle) {
        this.meIdle = idle;
        updateMyTurn();
        firebaseController.writeToDb(roomModel.roomId + "." + myKey + ".idle", this.meIdle);
    }

    @Override
    public boolean getIdle() {
        System.out.println("getIdle() -> " + meIdle + " " + opponentIdle);
        return meIdle && opponentIdle;
    }

    @Override
    public boolean canPerformAction() {
        System.out.println("canPerformAction() -> " + myTurn + " " + getIdle());
        return myTurn && getIdle();
    }

    public void setMyBallType(BallType ballType) {
        myBallType = ballType;
        opponentBallType = myBallType == BallType.STRIPED ? BallType.SOLID : BallType.STRIPED;
        firebaseController.stopListenToBallType();
        firebaseController.writeToDb(roomModel.roomId + ".ballTypes." + myKey, myBallType);
        firebaseController.writeToDb(roomModel.roomId + ".ballTypes." + opponentKey, opponentBallType);
    }

    public int getNumberOfShots() {
        return -1;
    }

    @Override
    public void incNumberOfShots() {
    }

    @Override
    public void reset() {
    }

    public void updateMyTurn() {
        myTurn = isHost == roomModel.hostTurn;
        System.out.println("updateMyTurn("+myTurn+")");
    }

    @Override
    public boolean getIsMyTurn() {
        return myTurn;
    }
}
