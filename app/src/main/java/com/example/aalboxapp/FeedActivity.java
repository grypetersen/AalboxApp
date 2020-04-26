package com.example.aalboxapp;
// https://androidwave.com/bottom-navigation-bar-android-example/
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedActivity extends AppCompatActivity {
    public FeedActivity() {
        // Required empty public constructor
    }
    BottomNavigationView bottomNavigation;
    BottomNavigationView TopNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        bottomNavigation = findViewById(R.id.top_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationTopItemSelectedListener);
    };

    // Bottom navigation. "overridePendingTransition" controls the animation.
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_map:
                            Log.i("Map", "Opens the map.");
                            startActivity(new Intent(FeedActivity.this, MapActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.navigation_feed:
                            Log.i("Feed", "The feed opens at home.");
                            startActivity(new Intent(FeedActivity.this, FeedActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            };

    BottomNavigationView.OnNavigationItemSelectedListener navigationTopItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_all:
                            Log.i("All", "Opens the all the posts.");
                            startActivity(new Intent(FeedActivity.this, MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.navigation_liked:
                            Log.i("Liked", "Opens the users liked posts.");
                            startActivity(new Intent(FeedActivity.this, MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.navigation_yourposts:
                            Log.i("Your Posts", "Opens the users posts.");
                            startActivity(new Intent(FeedActivity.this, MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            };
}