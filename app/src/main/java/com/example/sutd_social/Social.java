package com.example.sutd_social;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Social {
    private static final Social ourInstance = new Social();
    private static final String TAG = "SocialSingleton";
    private static DatabaseReference myRef;

    // Initialise the data attributes
    public static HashMap<String, String> name;
    public static HashMap<String, String> info;
    public static HashMap<String, String> pillar;
    public static HashMap<String, HashMap<String, Long>> skills;
    private static HashSet<String> strHeader = new HashSet<>(Arrays.asList("Name", "Info", "Pillar", "Skills"));

    static Social getInstance() {
        return ourInstance;
    }

    private Social() {
        // Query from Firebase and get the required information

        //Getting Firebase Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        myRef = database.getReference();

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

    public static void setAttr(String detail, String id, String value) {
        // Overloaded method for Name, Info, Pillar
        if (strHeader.contains(detail)) {
            myRef.child(detail).child(id).setValue(value);
        } else {
            throw new IllegalArgumentException("detail can only be: " + strHeader.toString());
        }
    }

    public static void setAttr(String detail, String id, HashMap<String, Long> value) {
        // Overloaded method for Skills
        if (detail == "Skills") {
            for (String key: value.keySet()) {
                myRef.child(detail).child(id).child(key).setValue(value.get(key));
            }
        } else {
            throw new IllegalArgumentException("detail can only be: Skills");
        }

    }

    public static void addUser() {
        // Find next possible id

        // TODO: Check that attributes are refreshed instantly
    }

    public static void rmUser(String id) {

    }
}
