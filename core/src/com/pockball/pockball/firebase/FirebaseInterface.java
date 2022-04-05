package com.pockball.pockball.firebase;

import com.pockball.pockball.db_models.ShotEvent;

public interface FirebaseInterface {
    // Interacting with platform specific code as suggested in LibGDX docs
    // https://libgdx.com/wiki/app/interfacing-with-platform-specific-code

    public void writeToDb(String target, Object value);
    public void addNewShot(String gameId, ShotEvent shotEvent);
    public void appendToArrayInDb(String target, Object value);

    public void listenToClientsInGame(String target);
    public void stopListenToClientsInGame();

    public void listenToPlayerEvents(String target);
    public void stopListenToEventsChanges();

    public void listenToHostTurn(String target);
    public void stopListenToHostTurn();

    public void listenToOpponentIdleState(String target);
    public void stopListenToOpponentIdleState();

    public void listenToAvailableRooms();
    public void stopListenToAvailableRooms();

    public void listenToBallType(String roomId);
    public void stopListenToBallType();
}
