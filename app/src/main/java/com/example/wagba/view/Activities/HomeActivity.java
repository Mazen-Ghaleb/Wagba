package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wagba.view.Fragments.HomeFragment;
import com.example.wagba.view.Fragments.OffersFragment;
import com.example.wagba.view.Fragments.OrderHistoryFragment;
import com.example.wagba.view.Fragments.SearchFragment;
import com.example.wagba.view.Fragments.SettingsFragment;
import com.example.wagba.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch(item.getItemId()) {
                    case R.id.Home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.Offers:
                        selectedFragment = new OffersFragment();
                        break;
                    case R.id.Search:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.Settings:
                        selectedFragment = new SettingsFragment();
                        break;
                    case R.id.order_history:
                        selectedFragment = new OrderHistoryFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
    };

    public void signOut(){
        googleSignInClient.signOut();
        firebaseAuth.signOut();

        startActivity(new Intent(HomeActivity.this,SignupActivity.class));
        finish();
    }

    public String getUserName(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        return firebaseUser.getDisplayName();
    }

    String getUserImage() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        assert firebaseUser != null;
        return Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
    }

    public void setProfileData(TextView textView){
        textView.setText("Welcome "+getUserName());
    }

    public void setProfileData(ImageView imageView){
        Glide.with(this).load(getUserImage()).into(imageView);
    }

    public void setProfileData(TextView textView, ImageView imageView){
        textView.setText("Welcome "+getUserName());
        Glide.with(this).load(getUserImage()).into(imageView);
    }
}