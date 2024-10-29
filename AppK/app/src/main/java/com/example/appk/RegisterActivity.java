package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName, registerEmail, registerPassword, teacherCodeField;
    private RadioButton radioTeacher, radioStudent;
    private Button registerButton;
    private DatabaseHelper db;

    private static final String TEACHER_CODE = "TeacherPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        teacherCodeField = findViewById(R.id.teacherCodeField);
        radioTeacher = findViewById(R.id.radioTeacher);
        radioStudent = findViewById(R.id.radioStudent);
        registerButton = findViewById(R.id.registerButton);

        teacherCodeField.setVisibility(View.GONE);

        radioTeacher.setOnClickListener(v -> teacherCodeField.setVisibility(View.VISIBLE));
        radioStudent.setOnClickListener(v -> teacherCodeField.setVisibility(View.GONE));

        registerButton.setOnClickListener(v -> {
            String name = registerName.getText().toString();
            String email = registerEmail.getText().toString();
            String password = registerPassword.getText().toString();
            String role = "";

            if (radioTeacher.isChecked()) {
                role = "teacher";
                String teacherCode = teacherCodeField.getText().toString();

                if (!teacherCode.equals(TEACHER_CODE)) {
                    Toast.makeText(RegisterActivity.this, "Invalid teacher code", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (radioStudent.isChecked()) {
                role = "student";
            }

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hash the password
            String hashedPassword = hashPassword(password);

            boolean isInserted = db.addUser(name, email, hashedPassword, role);
            if (isInserted) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

