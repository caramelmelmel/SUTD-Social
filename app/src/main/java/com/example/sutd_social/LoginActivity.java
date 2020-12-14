package com.example.sutd_social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sutd_social.firebase.Admin;
import com.example.sutd_social.firebase.BulletinBoard;
import com.example.sutd_social.firebase.Social;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText emailET;
    private EditText password;
    private EditText cfm_pw;
    private Button signInButton;
    private TextView signUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        firebaseAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        signUpTv = findViewById(R.id.signupTv);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // user is logged in
            Log.d(TAG, "User is already logged in");
            Admin.init(firebaseAuth.getCurrentUser().getUid());
            Social.getInstance();
            BulletinBoard.getInstance();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            // Time for firebase to fetch data
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        } else {
            Toast.makeText(LoginActivity.this,"You are not logged in",LENGTH_LONG);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }

        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }

        });

    }

    private void Login() {
        String email = emailET.getText().toString();
        String password1 = password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            //refers to the edittext
            emailET.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            password.setError("Enter your password");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        Log.d(TAG, "Login current user");
        firebaseAuth.signInWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Successfully Registered", LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); //DashBoardActivity
                    startActivity(intent);
                    Admin.init(firebaseAuth.getCurrentUser().getUid());
                    Social.getInstance();
                    BulletinBoard.getInstance();
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Sign In failed!", LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

}


