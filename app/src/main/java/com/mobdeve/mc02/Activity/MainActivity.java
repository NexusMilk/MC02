package com.mobdeve.mc02.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobdeve.mc02.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set the selected item programmatically to avoid unnecessary clicks
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        // Handle item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            int itemId = item.getItemId();

            // Handle navigation based on the selected item
            if (itemId == R.id.bottom_home) {
                // Stay on current activity
                return true;
            } else if (itemId == R.id.bottom_profile) {
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
                return true;
            } else if (itemId == R.id.bottom_settings) {
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
                return true;
            } else {
                return false;
            }
        });

        // Button 1 - TODO List
        ConstraintLayout btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
        });

        // Button 2 - Planner
        ConstraintLayout btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReminderActivity.class); // Make sure you have PlannerActivity
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Optional animations
        });

        // Button 3 - Reminder
        ConstraintLayout btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskPlannerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
        });

        // Button 4 - Camera
        ConstraintLayout btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left); // Add your animations
        });
    }
}
