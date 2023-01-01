package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app");

        // Used to test database rules
//        firebaseDatabase.getReference("Users").child("BsXR48vimob6KGv0b0VUzg7Lx9s2").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot attr : snapshot.getChildren()) {
//                    Log.v("test",attr.getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    protected void switchPage(Class activityClass){
        switchPage(activityClass,"",false);
    }

    protected void switchPage(Class activityClass, boolean finish){
        switchPage(activityClass,"",finish);
    }

    protected void switchPage(Class activityClass, String intentMessage){
        switchPage(activityClass,intentMessage,false);
    }

    protected void switchPage(Class activityClass, String intentMessage, boolean finish){
        Intent intent = new Intent(BaseActivity.this, activityClass);
        if (!intentMessage.isEmpty()) {
            intent.putExtra("intentMessage",intentMessage);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }
    protected String getIntentMessage(){
        return getIntent().getExtras().getString("intentMessage");
    }

}