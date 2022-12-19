package com.example.wagba.view.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wagba.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SigninActivity extends AppCompatActivity {

    Button sign_in_btn;
    SignInButton googleSignInButton;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    static int RC_SIGN_IN = 3331;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        googleSignInButton = findViewById(R.id.google_sign_in_btn);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
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
    void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle (GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this,authResult -> {
                    acct.getIdToken(); // We can save client stuff here in db
                    startActivity(new Intent(SigninActivity.this,HomeActivity.class));
                    finish();
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                });
    }
}
