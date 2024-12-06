package com.mobdeve.mc02.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.mc02.R;

public class AccountInfoActivity extends AppCompatActivity {
    private TextView tvAccountUsername;
    private TextView tvAccountEmail;
    private TextView tvAccountPassword;
    private SharedPreferences sharedPreferences;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        // Initialize Views
        tvAccountUsername = findViewById(R.id.tvAccountUsername);
        tvAccountPassword = findViewById(R.id.tvAccountPassword);
        tvAccountEmail = findViewById(R.id.tvAccountEmail);
        btnBack = findViewById(R.id.btnBack);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Display user information
        displayAccountInfo();

        // Set Back Button functionality
        btnBack.setOnClickListener(v -> finish());  // Finish activity and go back to previous screen
    }

    private void displayAccountInfo() {
        // Retrieve username, email, and password from SharedPreferences
        String username = sharedPreferences.getString("username", "Not Available");
        String email = sharedPreferences.getString("email", "Not Available");
        String password = sharedPreferences.getString("password", "Not Available");

        // Set text to display the retrieved information
        tvAccountUsername.setText("Username: " + username);
        tvAccountEmail.setText("Email: " + email);
        tvAccountPassword.setText("Password: " + password);
    }
}
