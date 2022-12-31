package com.example.wagba.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app");
    }

    protected void switchPage(Class activityClass){
        switchPage(activityClass,false);
    }

    protected void switchPage(Class activityClass, boolean finish){
        Intent intent = new Intent(BaseActivity.this, activityClass);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }
}