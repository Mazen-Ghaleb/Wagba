package com.example.wagba.view.Fragments;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wagba.Constants;
import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app");
    }

    protected void switchFragment(@IdRes int fragmentResource,@NonNull Fragment fragment){
        switchFragment(fragmentResource, fragment,"");
    }

    protected void switchFragment(@IdRes int fragmentResource,@NonNull Fragment fragment, String fragmentMessage){
        if (fragmentMessage.isEmpty()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentResource,
                    fragment).commit();
        }else {
            Bundle b = new Bundle();
            b.putString(Constants.MESSAGE_KEY, fragmentMessage);
            fragment.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentResource,
                    fragment).commit();
        }
    }

    protected String getFragmentMessage(){
        return getArguments().getString(Constants.MESSAGE_KEY);
    }
}
