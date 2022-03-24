package com.pockball.pockball.firebase;

import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.db_models.ShotModel;

public interface FirebaseInterface {
    // Interacting with platform specific code as suggested in LibGDX docs
    // https://libgdx.com/wiki/app/interfacing-with-platform-specific-code

    public void writeToDb(String target, Object value);
    public void addNewShot(String gameId, ShotModel shotModel);
    public void appendToArrayInDb(String target, Object value);

    public void listenToClientsInGame(String target);
    public void stopListenToClientsInGame();

    public void listenToShotChanges(String target);
    public void stopListenToShotChanges(String target);

    public void listenToHostTurn(String target);
    public void stopListenToHostTurn(String target);

    // TODO: Not using
    public void listenToAvailableRooms();
    public void stopListenToAvailableRooms();
}
