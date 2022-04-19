package com.pockball.pockball.firebase;

import com.pockball.pockball.db_models.ShotEvent;

public interface FirebaseInterface {
    // Interacting with platform specific code as suggested in LibGDX docs
    // https://libgdx.com/wiki/app/interfacing-with-platform-specific-code

    void writeToDb(String target, Object value);
    void removeFromDb(String target);

    void addNewShot(String gameId, ShotEvent shotEvent);
    void appendToArrayInDb(String target, Object value);

    void listenToClientsInGame(String target);
    void stopListenToClientsInGame();

    void listenToPlayerEvents(String target);
    void stopListenToEventsChanges();

    void listenToHostTurn(String target);
    void stopListenToHostTurn();

    void listenToOpponentIdleState(String target);
    void stopListenToOpponentIdleState();

    void getRoom(String roomId);
    void checkRoomId(String roomId);

    void listenToBallType(String roomId);
    void stopListenToBallType();
}
