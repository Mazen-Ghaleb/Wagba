package com.example.wagba.view.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;

public class BaseFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app");
    }

}
