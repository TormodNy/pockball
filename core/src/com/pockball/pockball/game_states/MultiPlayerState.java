package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.db_models.PlaceBallEvent;
import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.db_models.ShotEvent;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.firebase.FirebaseController;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerState implements State {

    private final Entity myPlayer, opponentPlayer;
    private boolean firstBall;
    private float gameVolume;

    private RoomModel roomModel;
    private boolean isHost;
    private FirebaseController firebaseController;
    private String myKey, opponentKey;
    private boolean myTurn;
    private boolean meIdle;
    private BallType myBallType, opponentBallType;

    private boolean opponentIdle;

    public MultiPlayerState(RoomModel roomModel, boolean isHost) {
        this.roomModel = roomModel;
        this.isHost = isHost;
        this.myTurn = this.isHost && roomModel.hostTurn;
        this.firstBall = true;

        // Keys. Is used for getting and listening to db
        myKey = isHost ? "host" : "client";
        opponentKey = isHost ? "client" : "host";

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
        myPlayer = EntityFactory.getInstance().createPlayer("player1");
        opponentPlayer = EntityFactory.getInstance().createPlayer("player2");
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
                // Set ball type on players if it is first ball to fall down
                if (firstBall) {
                    // Only set once
                    firstBall = false;
                    // Write ballType to db
                    setMyBallType(ballType);
                }

                if (getCurrentPlayerBallType() == null) break;

                if (getCurrentPlayerBallType().equals(ballType)) {
                    getActivePlayerModel().score.add(ballType.toString());
                } else {
                    setNextPlayerTurn();
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

    private Entity getInactivePlayer() {
        if (!roomModel.hostTurn) return myPlayer;
        return opponentPlayer;
    }

    private void setNextPlayerTurn() {
        roomModel.hostTurn = !roomModel.hostTurn;
        firebaseController.writeToDb(roomModel.roomId + ".hostTurn", roomModel.hostTurn);
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[2];
        players[0] = myPlayer;
        players[1] = opponentPlayer;
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
        firstBall = false;
        firebaseController.stopListenToBallType();
    }

    @Override
    public void setHostTurn(boolean hostTurn) {
        System.out.println("setHostTurn() -> " + hostTurn);
        roomModel.hostTurn = hostTurn;

    }

    @Override
    public void setIdle(boolean idle) {
        this.meIdle = idle;
        updateMyTurn();
        firebaseController.writeToDb(roomModel.roomId + "." + myKey + ".idle", this.meIdle);
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
    }

    @Override
    public boolean getIsMyTurn() {
        return myTurn;
    }
}
