package com.mobdeve.mc02.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.mc02.R;

public class AccountInfoActivity extends AppCompatActivity {
    private TextView tvAccountEmail, tvAccountPassword;
    private SharedPreferences sharedPreferences;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        // Initialize the Back Button
        btnBack = findViewById(R.id.btnBack);

        // Set up click listener for the Back button
        btnBack.setOnClickListener(v -> finish()); // Finish the current activity and go back to ProfileActivity

        // Initialize Views
        tvAccountEmail = findViewById(R.id.tvAccountEmail);
        tvAccountPassword = findViewById(R.id.tvAccountPassword);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Display user information
        displayAccountInfo();
    }

    private void displayAccountInfo() {
        // Retrieve email and password from SharedPreferences
        String email = sharedPreferences.getString("email", "Not Available");
        String password = sharedPreferences.getString("password", "Not Available");

        // Set text to display the retrieved information
        tvAccountEmail.setText("Email: " + email);
        tvAccountPassword.setText("Password: " + password);
    }
}
