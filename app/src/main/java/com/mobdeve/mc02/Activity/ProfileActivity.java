package com.mobdeve.mc02.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobdeve.mc02.R;

public class ProfileActivity extends AppCompatActivity {
    private Button personalInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        personalInfoButton = findViewById(R.id.button3);

        // Set up Personal Information Button
        personalInfoButton.setOnClickListener(v -> {
            // Start AccountInfoActivity to show account details
            Intent intent = new Intent(ProfileActivity.this, AccountInfoActivity.class);
            startActivity(intent);
        });

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set the selected item programmatically to avoid unnecessary clicks
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        // Handle item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;

            if (item.getItemId() == R.id.bottom_home) {
                intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right); // Add your animations
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                // Stay on current activity
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
                return true;
            }

            return false;
        });
    }
}
