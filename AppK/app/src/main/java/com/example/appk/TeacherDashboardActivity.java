package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherDashboardActivity extends AppCompatActivity {

    private TextView teacherNameTextView, totalStudentsTextView;
    private Button addActivityButton, manageClassesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        teacherNameTextView = findViewById(R.id.teacherNameTextView);
        totalStudentsTextView = findViewById(R.id.totalStudentsTextView);
        addActivityButton = findViewById(R.id.addActivityButton);
        manageClassesButton = findViewById(R.id.manageClassesButton);

        // Setup action for adding a new activity
        addActivityButton.setOnClickListener(view -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, CreateActivityActivity.class);
            startActivity(intent);
        });

        // Setup action for managing classes
        manageClassesButton.setOnClickListener(view -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, ManageClassesActivity.class);
            startActivity(intent);
        });
    }
}
