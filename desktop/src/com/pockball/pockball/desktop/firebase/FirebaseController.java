package com.pockball.pockball.desktop.firebase;

import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.db_models.ShotEvent;
import com.pockball.pockball.firebase.FirebaseInterface;

public class FirebaseController implements FirebaseInterface {

    @Override
    public void writeToDb(String target, Object value) {

    }

    @Override
    public void removeFromDb(String target) {

    }

    @Override
    public void listenToClientsInGame(String target) {

    }

    @Override
    public void stopListenToClientsInGame() {

    }

    @Override
    public void addNewShot(String gameId, ShotEvent shotEvent) {

    }

    @Override
    public void appendToArrayInDb(String target, Object value) {

    }

    @Override
    public void listenToPlayerEvents(String target) {

    }

    @Override
    public void stopListenToEventsChanges() {

    }

    @Override
    public void listenToHostTurn(String target) {

    }

    @Override
    public void stopListenToHostTurn() {

    }

    @Override
    public void listenToOpponentIdleState(String target) {

    }

    @Override
    public void stopListenToOpponentIdleState() {

    }

    @Override
    public void getRoom(String roomId) {

    }

    @Override
    public void listenToBallType(String roomId) {

    }

    @Override
    public void stopListenToBallType() {

    }

}
