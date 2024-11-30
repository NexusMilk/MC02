package com.mobdeve.mc02.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.mobdeve.mc02.R;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton, backButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Set Register Button Click Listener
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                // Save user credentials to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                // Clear reminders when a new account is created
                clearReminders();

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

                // Navigate to LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish(); // Close RegisterActivity
            } else {
                Toast.makeText(this, "Please fill in all fields and confirm the password correctly", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button to return to the previous activity
        backButton.setOnClickListener(v -> finish());
    }

    // Function to clear reminders from SharedPreferences
    private void clearReminders() {
        SharedPreferences reminderPreferences = getSharedPreferences("reminder_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = reminderPreferences.edit();

        // Save an empty list to reset reminders
        editor.putString("REMINDER_KEY", new Gson().toJson(new ArrayList<>()));
        editor.apply();
    }
}
