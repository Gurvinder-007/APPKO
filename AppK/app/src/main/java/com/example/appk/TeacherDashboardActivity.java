package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherDashboardActivity extends AppCompatActivity {

    private Button btnCreateActivity, btnReviewSubmissions, btnAssignClass, btnGiveFeedback, btnViewFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard); // Ensure this layout is the correct one

        // Initialize the buttons
        btnCreateActivity = findViewById(R.id.btnCreateActivity);
        btnReviewSubmissions = findViewById(R.id.btnReviewSubmissions);
        btnGiveFeedback = findViewById(R.id.btnGiveFeedback); // Initialize the new feedback button
        btnViewFeedback = findViewById(R.id.btnViewFeedback); // Initialize the view feedback button

        // Set click listeners
        btnCreateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreateActivityActivity
                Intent intent = new Intent(TeacherDashboardActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        btnReviewSubmissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ReviewSubmissionActivity
                Intent intent = new Intent(TeacherDashboardActivity.this, ReviewSubmissionActivity.class);
                startActivity(intent);
            }
        });

        btnGiveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to FeedbackActivity
                Intent intent = new Intent(TeacherDashboardActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the view feedback button
        btnViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to FeedbackViewActivity
                Intent intent = new Intent(TeacherDashboardActivity.this, ViewFeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}

