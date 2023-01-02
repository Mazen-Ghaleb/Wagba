package com.example.wagba.view.Fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wagba.R;
import com.example.wagba.data.Profile;
import com.example.wagba.data.ProfileDao;
import com.example.wagba.view.Activities.HomeActivity;
import com.example.wagba.viewModel.ProfileViewModel;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends BaseFragment {

    TextView userName;
    TextView signOut;
    TextView changeEmail;
    TextView changeFirstName;
    TextView changeLastName;
    ProfileViewModel mProfileViewModel;
    Profile userProfile;

    ImageView userImage;

    private List<Profile> mAllProfiles; // Cached copy of words


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment,container,false);

        userName = (TextView) root.findViewById(R.id.user_name);
        userImage = (ImageView) root.findViewById(R.id.user_image);
        signOut = (TextView) root.findViewById(R.id.sign_out_btn);

        changeFirstName = (TextView) root.findViewById(R.id.change_firstName);
        changeLastName = (TextView) root.findViewById(R.id.change_lastName);
        changeEmail = (TextView) root.findViewById(R.id.change_email);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).signOut();
            }
        });
        mProfileViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);

        changeFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change First Name");

                // Set up the input
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Updates ROOM
                        if (TextUtils.isEmpty(input.getText())) {
                            Toast.makeText(getContext(), "First name can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Profile tempProfile = new Profile(userProfile);
                        tempProfile.setFirstName(input.getText().toString());
                        mProfileViewModel.updateProfile(tempProfile);

                        // Updates firebase Database
                        firebaseDatabase.getReference("Users").child(firebaseAuth.getUid())
                                .child("firstName").setValue(input.getText().toString());

                        Log.v("test",firebaseAuth.getCurrentUser().getDisplayName());
                        // Updates firebase Authentication
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(input.getText().toString()+" "+
                                        firebaseAuth.getCurrentUser().getDisplayName().split("\\s+")[1])
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(profileUpdates);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        changeLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Last Name");

                // Set up the input
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(input.getText())) {
                            Toast.makeText(getContext(), "Last name can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Updates ROOM
                        Profile tempProfile = new Profile(userProfile);
                        tempProfile.setLastName(input.getText().toString());
                        mProfileViewModel.updateProfile(tempProfile);

                        // Updates firebase Database
                        firebaseDatabase.getReference("Users").child(firebaseAuth.getUid())
                                .child("lastName").setValue(input.getText().toString());

                        // Updates firebase Authentication
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(firebaseAuth.getCurrentUser().getDisplayName().split("\\s+")[0]
                                +" "+input.getText().toString())
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(profileUpdates);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Email");

                // Set up the input
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(input.getText())) {
                            Toast.makeText(getContext(), "Email can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Updates ROOM
                        Profile tempProfile = new Profile(userProfile);
                        tempProfile.setEmail(input.getText().toString());
                        mProfileViewModel.updateProfile(tempProfile);

                        // Updates firebase Database
                        firebaseDatabase.getReference("Users").child(firebaseAuth.getUid())
                                .child("email").setValue(input.getText().toString());

                        // Updates firebase Authentication
                        firebaseAuth.getCurrentUser().updateEmail(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        mProfileViewModel.getProfileById(firebaseAuth.getUid()).observe(getActivity(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if (profile == null){
                    return;
                }
                    userProfile = profile;
                    userName.setText(profile.getFirstName()+ " "+ profile.getLastName());
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mProfileViewModel.countProfiles().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer count) {
//                Log.d("test", "Number of profiles: " + count);
//            }
//        });
    }

}
