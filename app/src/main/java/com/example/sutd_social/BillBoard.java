package com.example.sutd_social;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BillBoard {
    private static final BillBoard ourInstance = new BillBoard();
    private static final String TAG = "SocialSingleton";

    static BillBoard getInstance() {
        return ourInstance;
    }

    private BillBoard() {
        //Getting Firebase Instance
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();

        //Getting reference to "Social" node
        myRef = myRef.child("Billboard");

        //Adding eventListener to reference
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //Getting User object from dataSnapshot
                    User user = data.getValue(User.class);
                    String username = user.getUserName();
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    Date dob = user.getDob();
                    String day = dob.getDay();
                    String month = dob.getMonth();
                    String year = dob.getYear();

                    Log.i(TAG, "onDataChange: " + username);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "onCancelled: Error: " + databaseError.getMessage());

            }
        });

    }
}
