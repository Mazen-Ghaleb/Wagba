package com.example.wagba.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wagba.R;
import com.example.wagba.view.Activities.HomeActivity;

public class SettingsFragment extends Fragment {

    TextView userName;
    TextView signOut;
    ImageView userImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment,container,false);

        userName = (TextView) root.findViewById(R.id.user_name);
        userImage = (ImageView) root.findViewById(R.id.user_image);
        signOut = (TextView) root.findViewById(R.id.sign_out_btn);

        userName.setText(((HomeActivity)getActivity()).getUserName());
        ((HomeActivity)getActivity()).setProfileData(userImage);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).signOut();
            }
        });

        return root;
    }
}
