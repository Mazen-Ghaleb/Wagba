package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.wagba.R;
import com.example.wagba.data.Profile;
import com.example.wagba.viewModel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseActivity {

//    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot user) {
                            ProfileViewModel mProfileViewModel = new ViewModelProvider(MainActivity.this).get(ProfileViewModel.class);
                            Profile profile = new Profile(
                                    user.child("id").getValue().toString(),
                                    user.child("firstName").getValue().toString(),
                                    user.child("lastName").getValue().toString(),
                                    user.child("email").getValue().toString(),
                                    user.child("birthdate").getValue().toString(),
                                    user.child("gender").getValue().toString()
                            );
                            mProfileViewModel.insert(profile);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    switchPage(HomeActivity.class, true);
                }
            }
        }, 1000);
    }
}