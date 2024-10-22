package com.example.appk;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    ProfileDAO profileDAO; // Your database access object for user profiles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        profileDAO = new ProfileDAO(this); // Initialize the DAO

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the user exists in the database
                if (profileDAO.validateUser(name, password)) {
                    // Get the role of the user
                    String role = profileDAO.getRole(name);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Redirect to the correct dashboard based on role
                    if (role.equals("Teacher")) {
                        Intent intent = new Intent(LoginActivity.this, TeacherDashboardActivity.class);
                        startActivity(intent);
                    } else if (role.equals("Student")) {
                        Intent intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
                        startActivity(intent);
                    }
                    finish(); // Close the LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirect to register page
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateCredentials(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

