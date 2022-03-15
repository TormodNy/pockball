package com.pockball.pockball.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pockball.pockball.db_models.GameModel;
import com.pockball.pockball.screens.create_game.CreateGameController;

public class FirebaseService implements FirebaseInterface {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseController firebaseController;
    private static final String TAG = "ReadAndWriteSnippets";

    private ValueEventListener roomListener;
    private DatabaseReference roomRef;

    public FirebaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://pockball-a5e58-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        firebaseController = FirebaseController.getInstance();
    }

    @Override
    public void writeToDb(String key, Object value) {
        databaseReference.child("test").child(key).setValue(value);
    }

    @Override
    public void listenToRoomChanges(String target) {
        // stores the reference in instance variable, to be able to unsubscribe from listener when needed
        roomRef = firebaseDatabase.getReference().child("test").child(target);

        // stores the listener in instance variable, to be able to unsubscribe from listener when needed
        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameModel gameModel = snapshot.getValue(GameModel.class);
                CreateGameController.getInstance().fireChangeInRoom(gameModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        };
        roomRef.addValueEventListener(roomListener);
    }


}

