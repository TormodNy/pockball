package com.pockball.pockball.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pockball.pockball.db_models.EventModel;
import com.pockball.pockball.db_models.PlaceBallEvent;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.screens.join_game_room.JoinGameController;

import java.util.ArrayList;
import java.util.List;

import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.db_models.ShotEvent;
import com.pockball.pockball.game_states.Context;
import com.pockball.pockball.screens.create_game_room.CreateGameRoomController;

public class FirebaseService implements FirebaseInterface {
    private DatabaseReference ref;
    private FirebaseDatabase db;
    private FirebaseController firebaseController;
    private static final String TAG = "ReadAndWriteSnippets";

    private ValueEventListener shotsListener, opponentListener, hostTurnListener;
    private DatabaseReference shotsRef, opponentRef, hostTurnReference;

    private ValueEventListener roomsListener;
    private DatabaseReference roomsRef;

    public FirebaseService() {
        db = FirebaseDatabase.getInstance("https://pockball-a5e58-default-rtdb.europe-west1.firebasedatabase.app");
        ref = db.getReference();
        firebaseController = FirebaseController.getInstance();
    }

    private DatabaseReference getRefFromNestedTarget(String nestedTarget) {
        String[] targets = nestedTarget.split("\\.");

        ref = db.getReference().child("test");

        for (String target : targets) {
            ref = ref.child(target);
        }

        return ref;
    }

    @Override
    public void writeToDb(String target, Object value) {
        ref = getRefFromNestedTarget(target);
        ref.setValue(value);
    }

    @Override
    public void listenToClientsInGame(String target) {
        opponentRef = getRefFromNestedTarget(target);

        opponentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlayerModel opponent = snapshot.getValue(PlayerModel.class);
                System.out.println(opponent);
                CreateGameRoomController.getInstance().notifyNewClient(opponent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println(error);
            }
        };

        opponentRef.addValueEventListener(opponentListener);
    }

    @Override
    public void stopListenToClientsInGame() {
        opponentRef.removeEventListener(opponentListener);
    }

    @Override
    public void addNewShot(String gameId, ShotEvent shotEvent) {
    }

    @Override
    public void appendToArrayInDb(String target, Object value) {
        ref = getRefFromNestedTarget(target).push();
        ref.setValue(value);
    }


    @Override
    public void listenToPlayerEvents(String target) {
        // stores the reference in instance variable, to be able to unsubscribe from listener when needed
        shotsRef = getRefFromNestedTarget(target);

        // stores the listener in instance variable, to be able to unsubscribe from listener when needed
        shotsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<EventModel> events = new ArrayList<>();
                for (DataSnapshot childSnap : snapshot.getChildren()) {
                    EventModel event = childSnap.getValue(EventModel.class);

                    switch (event.type) {
                        case SHOT:
                            event = childSnap.getValue(ShotEvent.class);
                            break;
                        case PLACE_BALL:
                            event = childSnap.getValue(PlaceBallEvent.class);
                            break;
                    }

                    events.add(event);
                }
                Context.getInstance().getState().fireOpponentEventChange(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        };
        shotsRef.addValueEventListener(shotsListener);
    }

    @Override
    public void stopListenToShotChanges(String target) {
        shotsRef.removeEventListener(shotsListener);
    }

    @Override
    public void listenToHostTurn(String gameId) {
        hostTurnReference = db
                .getReference("test")
                .child(gameId)
                .child("hostTurn");

        hostTurnListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean hostTurn = snapshot.getValue(Boolean.class);
                Context.getInstance().getState().setHostTurn(hostTurn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println(error);
            }
        };

        hostTurnReference.addValueEventListener(hostTurnListener);
    }

    @Override
    public void stopListenToHostTurn(String target) {
        hostTurnReference.removeEventListener(hostTurnListener);
    }

    @Override
    public void listenToAvailableRooms() {
        // stores the reference in instance variable, to be able to unsubscribe from listener when needed
        roomsRef = db.getReference().child("test");

        // stores the listener in instance variable, to be able to unsubscribe from listener when needed
        roomsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> rooms = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    RoomModel room = snap.getValue(RoomModel.class);
                    rooms.add(room);
                }

                JoinGameController.getInstance().setAvailableRooms(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        };

        roomsRef.addValueEventListener(roomsListener);
    }

    @Override
    public void stopListenToAvailableRooms() {
        roomsRef.removeEventListener(roomsListener);
    }


    public void writeToDb(String key, String value) {
        ref.child("test").child(key).setValue(value);
    }
}

