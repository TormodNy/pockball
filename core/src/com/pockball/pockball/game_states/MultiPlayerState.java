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


    // TODO: Remove event listener


    public MultiPlayerState(RoomModel roomModel, boolean isHost) {
        this.roomModel = roomModel;
        this.isHost = isHost;
        this.myTurn = this.isHost && roomModel.hostTurn;

        myKey = isHost ? "host" : "client";
        opponentKey = isHost ? "client" : "host";

        firebaseController = FirebaseController.getInstance();

        if (!this.isHost) {
            firebaseController.writeToDb(roomModel.roomId + ".client.playerId", "tormod");
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
    public void ballIntoHole(BallType ballType) {
        switch (ballType) {
            case WHITE:
                System.out.println("Next player! " +
                        getActivePlayer().getComponent(PlayerComponent.class).playerName +
                        " shot WHITE ball into hole.");
                setNextPlayerTurn();
                break;

            case BLACK:
                // TODO: Wrong logic
                setNextPlayerTurn();

                System.out.println(getActivePlayer().getComponent(PlayerComponent.class).playerName +
                        " lost! Shot BLACK ball into hole.");
                break;

            default:
                // Set ball type on players if it is first ball to fall down
                if (firstBall) {
                    // Only set once
                    firstBall = false;

                    // Write ballType to db
                    setMyBallType(ballType);

                    getActivePlayerModel().ballType = ballType;
                    getInactivePlayerModel().ballType =
                            ballType == BallType.STRIPED ? BallType.SOLID : BallType.STRIPED;


                }

                    setNextPlayerTurn();
                if (getActivePlayerModel().ballType == null) break;

                // TODO: Should increment value in db instead
                if (getActivePlayerModel().ballType.equals(ballType)) {
                    getActivePlayerModel().score.add(ballType.toString());

                    //firebaseController.writeToDb(roomModel.roomId, roomModel);
                }

                // TODO: Should the other player get points if one shoots down wrong ball?
                else {
                    setNextPlayerTurn();
                    System.out.println("Change");
                }

                System.out.println(
                        getActivePlayer().getComponent(PlayerComponent.class).playerName
                                + " shot ball " + ballType + " into hole.");
        }
    }

    // Should maybe be public to be able to show on screen
    private Entity getActivePlayer() {
        if (roomModel.hostTurn) return myPlayer;
        return opponentPlayer;
    }

    private PlayerModel getActivePlayerModel() {
        if (roomModel.hostTurn) return roomModel.host;
        return roomModel.client;
    }

    private PlayerModel getInactivePlayerModel() {
        if (!roomModel.hostTurn) return roomModel.host;
        return roomModel.client;
    }

    private String getActivePlayerTarget() {
        if (roomModel.hostTurn) return "host";
        return "opponent";
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
        firebaseController.writeToDb(roomModel.roomId + ".hostTurn", !roomModel.hostTurn);
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
        System.out.println("Opponent is idle. Can perform: " + canPerformAction());
    }

    @Override
    public void fireBallTypeSet(BallType hostBallType, BallType opponentBallType) {
        if (isHost) {
            myBallType = hostBallType;
            this.opponentBallType = opponentBallType;
        } else {
            this.opponentBallType = hostBallType;
            myBallType = hostBallType;
        }
        firebaseController.stopListenToBallType();
    }

    @Override
    public void setHostTurn(boolean hostTurn) {
        System.out.println("setHostTurn() -> " + hostTurn);
        roomModel.hostTurn = hostTurn;
    }

    @Override
    public void setIdle(boolean ready) {
        this.meIdle = ready;
        firebaseController.writeToDb(roomModel.roomId + "." + myKey + ".idle", this.meIdle);
    }

    @Override
    public boolean canPerformAction() {
        return meIdle && opponentIdle && myTurn;
    }

    public void setMyBallType(BallType ballType) {
        myBallType = ballType;
        opponentBallType = myBallType == BallType.STRIPED ? BallType.SOLID : BallType.STRIPED;
        firebaseController.stopListenToBallType();
        firebaseController.writeToDb(roomModel.roomId + ".ballTypes." + myKey, myBallType);
        firebaseController.writeToDb(roomModel.roomId + ".ballTypes." + opponentKey, opponentBallType);
    }

    public boolean isOpponentIdle() {
        return opponentIdle;
    }

    public void setOpponentIdle(boolean opponentIdle) {
        this.opponentIdle = opponentIdle;
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
}
