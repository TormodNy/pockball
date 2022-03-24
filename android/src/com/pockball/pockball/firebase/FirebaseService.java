package com.pockball.pockball.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pockball.pockball.db_models.RoomModel;
import com.pockball.pockball.screens.join_game_room.JoinGameController;

import java.util.ArrayList;
import java.util.List;
import com.pockball.pockball.db_models.PlayerModel;
import com.pockball.pockball.screens.create_game_room.CreateGameRoomController;
import com.pockball.pockball.screens.create_game_room.CreateGameRoomView;

public class FirebaseService implements FirebaseInterface {
    private DatabaseReference ref;
    private FirebaseDatabase db;
    private FirebaseController firebaseController;
    private static final String TAG = "ReadAndWriteSnippets";

    private ValueEventListener roomListener, opponentListener;
    private DatabaseReference roomRef, opponentRef;

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
    public void listenToOpponentInGame(String target) {
        opponentRef = getRefFromNestedTarget(target);

        opponentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlayerModel opponent = snapshot.getValue(PlayerModel.class);
                System.out.println(opponent);
                CreateGameRoomController.getInstance().notifyNewOpponent(opponent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println(error);
            }
        };

        opponentRef.addValueEventListener(opponentListener);
    }

    @Override
    public void stopListenToOpponentInGame() {
        opponentRef.removeEventListener(opponentListener);
    }

    @Override
    public void listenToRoomChanges(String target) {
        // stores the reference in instance variable, to be able to unsubscribe from listener when needed
        roomRef = db.getReference().child("test").child(target);

        // stores the listener in instance variable, to be able to unsubscribe from listener when needed
        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RoomModel roomModel = snapshot.getValue(RoomModel.class);
                CreateGameController.getInstance().fireChangeInRoom(roomModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        };
        roomRef.addValueEventListener(roomListener);
    }

    @Override
    public void listenToAvailableRooms() {
        // stores the reference in instance variable, to be able to unsubscribe from listener when needed
        roomsRef = firebaseDatabase.getReference().child("test");

        // stores the listener in instance variable, to be able to unsubscribe from listener when needed
        roomsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> rooms = new ArrayList<RoomModel>();
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
        databaseReference.child("test").child(key).setValue(value);
    }
}

