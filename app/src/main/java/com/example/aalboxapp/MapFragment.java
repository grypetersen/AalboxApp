package com.example.aalboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapFragment extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    };

    // Bottom navigation. "overridePendingTransition" controls the animation.
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_map:
                            Log.i("Map", "Opens the map.");
                            startActivity(new Intent(MapFragment.this, MapFragment.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_feed:
                            Log.i("Feed", "The feed opens at home.");
                            startActivity(new Intent(MapFragment.this, MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };
    }