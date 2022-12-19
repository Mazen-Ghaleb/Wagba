package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText fName;
    TextInputEditText lName;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    TextInputEditText dateOfBirth;
//    TextInputEditText Gender;

    TextView sign_in_btn;
    Button sign_up_btn;
    FirebaseAuth firebaseAuth;

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

        sign_in_btn = (TextView) findViewById(R.id.sign_in_here);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSignInPage();
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void createUser(){
        String authFName = fName.getText().toString();
        String authLName = lName.getText().toString();
        String authEmail = email.getText().toString();
        String authPassword = password.getText().toString();
        String authConfirmedPassword = confirmPassword.getText().toString();
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
        } else if (TextUtils.isEmpty(authDateOfBirth)) {
            dateOfBirth.setError("Date of Birth can not be empty");
            dateOfBirth.requestFocus();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(authEmail,authPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Signed Up successfully", Toast.LENGTH_SHORT).show();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(authFName+" "+authLName)
                                .setPhotoUri(Uri.parse("https://cdn-icons-png.flaticon.com/512/1077/1077114.png"))
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(profileUpdates);
                        firebaseAuth.signOut(); // To make sure user passes through Sign in page
                        switchToSignInPage();
                    } else {
                        Toast.makeText(SignupActivity.this, "Sign Up Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    private void switchToSignInPage(){
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
}