package com.mobdeve.mc02.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.mc02.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, backButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Set Back Button Click Listener
        backButton.setOnClickListener(v -> finish());

        // Set Login Button Click Listener
        loginButton.setOnClickListener(v -> {
            String enteredEmail = emailEditText.getText().toString();
            String enteredPassword = passwordEditText.getText().toString();

            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (enteredEmail.equals(savedEmail) && enteredPassword.equals(savedPassword)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Navigate to the next activity (e.g., MainActivity or Dashboard)
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
