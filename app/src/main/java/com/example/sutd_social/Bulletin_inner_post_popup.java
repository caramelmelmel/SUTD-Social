package com.example.sutd_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Bulletin_inner_post_popup extends Activity {

    private TextView textView_inner_title, textView_inner_description;
    private Button popUpBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_inner_post_popup);

        textView_inner_title = findViewById(R.id.txtView_title_inner);
        textView_inner_description = findViewById(R.id.txtView_Description_inner);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        popUpBackButton = findViewById(R.id.card_back);
        popUpBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        getWindow().setLayout((int)(width *0.83), (int)(height*0.85));

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.clear));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.3f;
        getWindow().setAttributes(params);
    }
}