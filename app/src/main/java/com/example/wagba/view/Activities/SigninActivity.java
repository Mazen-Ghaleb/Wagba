package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.view.AdapterData.UserData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;

    TextView sign_up_btn;
    Button sign_in_btn;
    SignInButton googleSignInButton;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    static int RC_SIGN_IN = 3331;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = (TextInputEditText) findViewById(R.id.email_val_sign_in);
        password = (TextInputEditText) findViewById(R.id.password_val_sign_in);
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_btn);
        sign_up_btn = (TextView) findViewById(R.id.sign_up_here);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://wagba-22208-default-rtdb.europe-west1.firebasedatabase.app");

        setGooglePlusButtonText(googleSignInButton,"Sign in with Google");

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSignIn();
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchPage(SignupActivity.class,true);;
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void emailSignIn(){
        String authEmail = email.getText().toString();
        String authPassword = password.getText().toString();

        if (TextUtils.isEmpty(authEmail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        } else if (TextUtils.isEmpty(authPassword)){
            password.setError("Password can not be empty");
            password.requestFocus();
        } else {
            firebaseAuth.signInWithEmailAndPassword(authEmail, authPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SigninActivity.this, "Signed In successfully", Toast.LENGTH_SHORT).show();
                        switchPage(HomeActivity.class,true);
                    } else{
                        Toast.makeText(SigninActivity.this, "Sign In Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void googleSignIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle (GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this,authResult -> {


                    // Saving user information in firebase Database
                    UserData user = new UserData(acct.getGivenName(), acct.getFamilyName(),acct.getEmail(),"","");

                    firebaseDatabase.getReference("Users")
                            .child(acct.getIdToken())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SigninActivity.this, "Sign In Success: ", Toast.LENGTH_SHORT).show();
                                    switchPage(HomeActivity.class,true);
                                }
                            });
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Sign In Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
    public void switchPage(Class activityClass, boolean finish){
        Intent intent = new Intent(SigninActivity.this, activityClass);
        startActivity(intent);
        if (finish) {finish();}
    }
}
