package com.mobdeve.mc02.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobdeve.mc02.R;

public class SettingsActivity extends AppCompatActivity {
    private Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize the Logout Button
        buttonLogout = findViewById(R.id.button4);

        // OnCLickListener for the Logout Button
        buttonLogout.setOnClickListener(v -> {
            // Clear session data stored in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            // Clear the back stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            finish();
        });

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set the selected item programmatically to avoid unnecessary clicks
        bottomNavigationView.setSelectedItemId(R.id.bottom_settings);

        // Handle item selection using if-else
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;

            if (item.getItemId() == R.id.bottom_home) {
                intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right); // Add your animations
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                // Stay on current activity
                return true;
            }

            return false; // Return false if no item was matched
        });
    }
}