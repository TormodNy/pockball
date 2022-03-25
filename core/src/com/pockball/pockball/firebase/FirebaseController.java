package com.pockball.pockball.firebase;

import com.pockball.pockball.db_models.ShotEvent;

public class FirebaseController implements FirebaseInterface {

    private FirebaseInterface firebaseInterface;
    // Singleton
    private static FirebaseController firebaseController = null;

    public FirebaseController() {
    }

    public static FirebaseController getInstance() {
        if (firebaseController == null) {
            firebaseController = new FirebaseController();
        }
        return firebaseController;
    }

    public void setFirebaseInterface(FirebaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    @Override
    public void writeToDb(String target, Object value) {
        firebaseInterface.writeToDb(target, value);
    }

    @Override
    public void listenToClientsInGame(String target) {
        firebaseInterface.listenToClientsInGame(target + ".client");
    }

    @Override
    public void stopListenToClientsInGame() {
        firebaseInterface.stopListenToClientsInGame();
    }

    @Override
    public void addNewShot(String gameId, ShotEvent shotEvent) {
        firebaseInterface.addNewShot(gameId, shotEvent);
    }

    @Override
    public void appendToArrayInDb(String target, Object value) {
        firebaseInterface.appendToArrayInDb(target, value);
    }

    @Override
    public void listenToShotChanges(String target) {
        firebaseInterface.listenToShotChanges(target);
    }

    @Override
    public void stopListenToShotChanges(String target) {
        firebaseInterface.stopListenToShotChanges(target);
    }

    @Override
    public void listenToHostTurn(String target) {
        firebaseInterface.listenToHostTurn(target);
    }

    @Override
    public void stopListenToHostTurn(String target) {
        firebaseInterface.stopListenToHostTurn(target);
    }

    @Override
    public void listenToAvailableRooms() {
        firebaseInterface.listenToAvailableRooms();
    }

    @Override
    public void stopListenToAvailableRooms() {
        firebaseInterface.stopListenToAvailableRooms();
    }
}
