package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.view.AdapterData.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignupActivity extends BaseActivity {

    TextInputEditText fName;
    TextInputEditText lName;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    TextInputEditText dateOfBirth;
    TabLayout genderPos;
    String gender;

    TextView sign_in_btn;
    Button sign_up_btn;
    String current;
    String initialDateOfBirth;
    Calendar calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        fName = (TextInputEditText) findViewById(R.id.fname_val_signup);
        lName = (TextInputEditText) findViewById(R.id.lname_val_signup);
        email = (TextInputEditText) findViewById(R.id.email_val_sign_up);
        password = (TextInputEditText) findViewById(R.id.password_val_sign_up);
        confirmPassword = (TextInputEditText) findViewById(R.id.confirm_password_val);
        dateOfBirth = (TextInputEditText) findViewById(R.id.birthday_val_sign_up);
        genderPos = (TabLayout) findViewById(R.id.tabLayout);
        gender = "Male"; // Initial Value

        sign_in_btn = (TextView) findViewById(R.id.sign_in_here);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);

        genderPos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                gender =tab.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        calender = Calendar.getInstance();
        initialDateOfBirth = "DDMMYYYY";
        current = "";

        dateOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + initialDateOfBirth.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        calender.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2022) ? 2022 : year;
                        calender.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > calender.getActualMaximum(Calendar.DATE)) ? calender.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateOfBirth.setText(current);
                    dateOfBirth.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchPage(SigninActivity.class, true);
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }

    private void createUser(){
        String authFName = fName.getText().toString();
        String authLName = lName.getText().toString();
        String authEmail = email.getText().toString();
        String authPassword = password.getText().toString();
        String authConfirmedPassword = confirmPassword.getText().toString();
        String authGender = gender;
        String authDateOfBirth = dateOfBirth.getText().toString();

        if (TextUtils.isEmpty(authFName)){
            fName.setError("First Name can not be empty");
            fName.requestFocus();
        } else if (TextUtils.isEmpty(authLName)){
            lName.setError("Last Name can not be empty");
            lName.requestFocus();
        } else if (TextUtils.isEmpty(authEmail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        } else if (TextUtils.isEmpty(authPassword)){
            password.setError("Password can not be empty");
            password.requestFocus();
        } else if (!TextUtils.equals(authPassword,authConfirmedPassword)) {
            confirmPassword.setError("Confirmed password has to match password");
            confirmPassword.requestFocus();
        } else if (TextUtils.isEmpty(authGender)) {
            Toast.makeText(this, "Error retrieving Gender", Toast.LENGTH_SHORT).show();
            genderPos.requestFocus();
        }else if (TextUtils.isEmpty(authDateOfBirth) || !authDateOfBirth.matches("[0-9/]+")) {
            dateOfBirth.setError("Date of Birth can not be empty");
            dateOfBirth.requestFocus();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(authEmail,authPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(authFName+" "+authLName)
                                .setPhotoUri(Uri.parse("https://cdn-icons-png.flaticon.com/512/1077/1077114.png"))
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(profileUpdates);

                        // Saving user information in firebase Database
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        UserData user = new UserData(userId, authFName,authLName,authEmail,authDateOfBirth,authGender);

                        firebaseDatabase.getReference("Users")
                                .child(userId)
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(SignupActivity.this, "Signed Up successfully", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut(); // To make sure user passes through Sign in page
                                        switchPage(SigninActivity.class, true);
                                    }
                                });
                    } else {
                        Toast.makeText(SignupActivity.this, "Sign Up Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}