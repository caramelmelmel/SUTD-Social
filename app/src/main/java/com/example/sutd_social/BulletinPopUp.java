package com.example.sutd_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sutd_social.firebase.Admin;
import com.example.sutd_social.firebase.Bulletin;
import com.example.sutd_social.firebase.BulletinBoard;

public class BulletinPopUp extends Activity {

    private Button btn_Confirm,btn_Cancel;
    private EditText edtTxtTitle;
    private EditText edtTxtDescription;
    private EditText edtTxteventdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_pop_up);

        edtTxtTitle = findViewById(R.id.edtTxt_Title);
        edtTxtDescription = findViewById(R.id.edtTxt_description);
        edtTxteventdate = findViewById(R.id.eventDate);
        btn_Confirm = findViewById(R.id.btn_Confirm);


        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCheck();
                // add the following stuff to the
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                String txtTitle = edtTxtTitle.getText().toString();
                String txtDescription = edtTxtDescription.getText().toString();
                String txtDate = edtTxteventdate.getText().toString();
                //adding to firebase
                BulletinBoard.addBulletin(Admin.getUserid(), new Bulletin(txtTitle,txtDescription));
                //Jun kai do your image stuff here

                //------------
                // rmb to do intent put extra for url like below here pls give "txtUrl" as name

                intent.putExtra("txtTitle", txtTitle);
                intent.putExtra("txtDescription", txtDescription);
                intent.putExtra("txtDate", txtDate);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *0.83), (int)(height*0.85));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

        //prompt the user by checking if the inputs are justified correctly
        private void confirmCheck(){
        //set the instance attributes for this
        String Txttitle = edtTxtTitle.getText().toString();
        if(Txttitle.isEmpty()){
            Toast.makeText(BulletinPopUp.this,"Please enter the event title!",Toast.LENGTH_LONG).show();
            return;
        }}


    }

