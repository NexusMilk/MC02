package com.mobdeve.mc02.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobdeve.mc02.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        Button personalInfoButton = findViewById(R.id.button3);

        // Set up Personal Information Button
        personalInfoButton.setOnClickListener(v -> {
            // Check if user info exists in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "Not Available");
            String email = sharedPreferences.getString("email", "Not Available");
            String password = sharedPreferences.getString("password", "Not Available");

            // Log to check if data is being retrieved correctly
            Log.d("ProfileActivity", "Username: " + username);
            Log.d("ProfileActivity", "Email: " + email);
            Log.d("ProfileActivity", "Password: " + password);

            // Proceed to AccountInfoActivity only if data is valid
            if (!username.equals("Not Available") && !email.equals("Not Available") && !password.equals("Not Available")) {
                Intent intent = new Intent(ProfileActivity.this, AccountInfoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ProfileActivity.this, "User data not found. Please register first.", Toast.LENGTH_SHORT).show();
            }
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
