package com.example.sutd_social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    //good practice to keep the names consistent with their ids
    private EditText emailET;
    private EditText password;
    private EditText cfm_pw;
    private Button signup;
    private TextView signInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        firebaseAuth = firebaseAuth.getInstance();
        emailET = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cfm_pw = findViewById(R.id.confirm);
        signup = findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);
        signInTv = findViewById(R.id.signInTv);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }

        });

        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }
    private void Register(){
        String email = emailET.getText().toString();
        String password1 = password.getText().toString();
        String password2 = cfm_pw.getText().toString();
        if(TextUtils.isEmpty(email)){
            //refers to the edittext
            emailET.setError("Enter your email");
        }
        else if(TextUtils.isEmpty(password1)){
            password.setError("Enter your password");
        }

        else if(TextUtils.isEmpty(password2)){
            cfm_pw.setError("Confirm your password");
        }

        //check if the user has entered the same password
        else if(password1!=password2){
            cfm_pw.setError("Please enter the same password");
        }

        //check for the length of password
        else if(password1.length()< 8){
            password.setError("Length should be more than 8");
        }
        //check if user enters email format correctly
        else if(!isValidEmail(email)){
            //refer to edit text
            password.setError("Enter in email format");
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this,new OnCompleteListener(){
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SignUpActivity.this, "Sign Up failed!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });}

    private boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());


    }

}
