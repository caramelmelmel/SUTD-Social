package com.example.sutd_social;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import static android.os.SystemClock.sleep;

//using FB auth

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Show login page to get email, password - can implement data persistence
        // Do consider this method to create account - mAuth.createUserWithEmailAndPassword()

        // Hard coding login
        String email = "testAccount@gmail.com";
        String pass = "testing123";
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmailAndPassword:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    // change to home page
                    setContentView(R.layout.activity_main);
                    Social.getInstance();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                    // Do a toast or something
                }
            }
        });
    }
}

