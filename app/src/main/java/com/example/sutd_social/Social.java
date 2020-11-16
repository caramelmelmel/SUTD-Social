package com.example.sutd_social;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Social {
    private static final Social ourInstance = new Social();
    private static final String TAG = "SocialSingleton";
    public static HashMap<String, String> name;
    public static HashMap<String, String> info;
    public static HashMap<String, String> pillar;
    public static HashMap<String, HashMap<String, Long>> skills;

    static Social getInstance() {
        return ourInstance;
    }

    private Social() {
        // Query from Firebase and get the required information

        //Getting Firebase Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();

        //Getting reference to "Social" node
        myRef = myRef.child("Social");

        //Adding eventListener to reference
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Getting the string value of that node
                Log.d(TAG,"Updating Singleton");
                name = (HashMap<String, String>) dataSnapshot.child("Name").getValue();
                info = (HashMap<String, String>) dataSnapshot.child("Info").getValue();
                pillar = (HashMap<String, String>) dataSnapshot.child("Pillar").getValue();
                skills = (HashMap<String, HashMap<String, Long>>) dataSnapshot.child("Skills").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );
            }
        });
    }
}
