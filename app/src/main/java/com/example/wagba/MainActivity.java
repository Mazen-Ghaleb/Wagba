package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (new Handler()).postDelayed(this::switchPage, 5000);

//        logo = (TextView) findViewById(R.id.Logo);
//        Typeface typeface = getResources().getFont(R.font.paisley_icg_02);
//        logo.setTypeface(typeface);
    }

    void switchPage(){
        Intent intent = new Intent(MainActivity.this,SignupActivity.class);
        startActivity(intent);
        finish();
    }
}