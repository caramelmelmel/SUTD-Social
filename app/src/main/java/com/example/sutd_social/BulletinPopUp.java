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

public class BulletinPopUp extends Activity {

    private Button btn_Confirm,btn_Cancel;
    private EditText edtTxtTitle;
    private EditText edtTxtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_pop_up);

        edtTxtTitle = findViewById(R.id.edtTxt_Title);
        edtTxtDescription = findViewById(R.id.edtTxt_description);

        btn_Confirm = findViewById(R.id.btn_Confirm);

        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                String txtTitle = edtTxtTitle.getText().toString();
                String txtDescription = edtTxtDescription.getText().toString();
                intent.putExtra("txtTitle", txtTitle);
                intent.putExtra("txtDescription", txtDescription);
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
}
