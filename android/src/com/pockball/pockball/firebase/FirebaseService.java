package com.pockball.pockball.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService implements FirebaseInterface {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseController firebaseController;
    private static final String TAG = "ReadAndWriteSnippets";

    public FirebaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://pockball-a5e58-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        firebaseController = FirebaseController.getInstance();
    }

    @Override
    public void writeToDb(String key, String value) {
        databaseReference.child("test").child(key).setValue(value);
    }
}

