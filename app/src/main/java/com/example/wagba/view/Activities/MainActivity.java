package com.example.wagba.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    TextView logo;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


//        logo = (TextView) findViewById(R.id.Logo);
//        Typeface typeface = getResources().getFont(R.font.paisley_icg_02);
//        logo.setTypeface(typeface);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Switch page after 1 second
                if (user== null){
                    switchPage(SignupActivity.class, true);
                }
                else {
                    switchPage(HomeActivity.class, true);
                }
            }
        }, 1000);
    }

    public void switchPage(Class activityClass, boolean finish){
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
        if (finish) {finish();}
    }
}