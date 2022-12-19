package com.example.wagba.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.wagba.view.Fragments.HomeFragment;
import com.example.wagba.view.Fragments.OffersFragment;
import com.example.wagba.view.Fragments.OrderHistoryFragment;
import com.example.wagba.view.Fragments.SearchFragment;
import com.example.wagba.view.Fragments.SettingsFragment;
import com.example.wagba.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

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
}