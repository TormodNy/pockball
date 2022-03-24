package com.pockball.pockball.firebase;

public class FirebaseController implements FirebaseInterface {

    private FirebaseInterface firebaseInterface;
    // Singleton
    private static FirebaseController firebaseController = null;

    public FirebaseController() {}

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
    public void listenToOpponentInGame(String target) {
        firebaseInterface.listenToOpponentInGame(target + ".opponent");
    }

    @Override
    public void stopListenToOpponentInGame() {
        firebaseInterface.stopListenToOpponentInGame();
    }

    @Override
    public void listenToRoomChanges(String target) {
        firebaseInterface.listenToRoomChanges(target);
    }

    @Override
    public void listenToAvailableRooms() {
        firebaseInterface.listenToAvailableRooms();
    }

    @Override
    public void stopListenToAvailableRooms() {
        firebaseInterface.stopListenToAvailableRooms();
    public void writeToDb(String key, String value) {
        firebaseInterface.writeToDb(key, value);
    }
}
