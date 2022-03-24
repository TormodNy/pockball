package com.pockball.pockball.firebase;

public interface FirebaseInterface {
    // Interacting with platform specific code as suggested in LibGDX docs
    // https://libgdx.com/wiki/app/interfacing-with-platform-specific-code

    public void writeToDb(String target, Object value);
    public void listenToRoomChanges(String target);
    public void listenToAvailableRooms();
    public void stopListenToAvailableRooms();
}
