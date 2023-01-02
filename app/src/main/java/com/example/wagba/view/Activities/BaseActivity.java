package com.example.wagba.view.Activities;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wagba.Constants;
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
            intent.putExtra(Constants.MESSAGE_KEY,intentMessage);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void switchFragment(@IdRes int fragmentResource,@NonNull Fragment fragment){
        switchFragment(fragmentResource, fragment,"");
    }

    protected void switchFragment(@IdRes int fragmentResource,@NonNull Fragment fragment, String fragmentMessage){
        if (fragmentMessage.isEmpty()){
            this.getSupportFragmentManager().beginTransaction().replace(fragmentResource,
                    fragment).commit();
        } else {
            Bundle b = new Bundle();
            b.putString(Constants.MESSAGE_KEY, fragmentMessage);
            fragment.setArguments(b);
            this.getSupportFragmentManager().beginTransaction().replace(fragmentResource,
                    fragment).commit();
        }
    }

    protected String getIntentMessage(){
        return getIntent().getExtras().getString(Constants.MESSAGE_KEY);
    }

}