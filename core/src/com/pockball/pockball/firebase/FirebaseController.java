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
    public void listenToRoomChanges(String target) {
        firebaseInterface.listenToRoomChanges(target);
    }
}
