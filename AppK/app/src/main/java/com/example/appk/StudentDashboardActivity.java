package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView studentName, feedbackText;
    private ListView assignedActivities;
    private Button submitWorkButton;
    private Button showActivitiesButton; // Button to show activities
    private Button viewFeedbackButton; // New button to view feedback
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard); // Ensure this layout contains all required views

        studentName = findViewById(R.id.student_name);
        feedbackText = findViewById(R.id.feedback_text);
        assignedActivities = findViewById(R.id.assigned_activities);
        submitWorkButton = findViewById(R.id.submit_work_button);
        showActivitiesButton = findViewById(R.id.show_activities_button); // Initialize the button
        viewFeedbackButton = findViewById(R.id.view_feedback_button); // Initialize the new feedback button
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        studentName.setText("Welcome, " + username);

        // Load assigned activities from database for this student
        // Load feedback for last submission

        // Button to submit work
        submitWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitIntent = new Intent(StudentDashboardActivity.this, SubmitWorkActivity.class);

                // Assuming you have methods to get the user ID and program ID
                int userId = db.getUserIdByUsername(username); // Retrieve user ID from database
                int programId = db.getProgramIdByName("Program Name"); // Replace with actual program name or ID

                submitIntent.putExtra("USER_ID", userId);
                submitIntent.putExtra("PROGRAM_ID", programId);
                startActivity(submitIntent);
            }
        });

        // Button to show activities
        showActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showActivitiesIntent = new Intent(StudentDashboardActivity.this, ShowActivitiesActivity.class);
                startActivity(showActivitiesIntent); // Navigate to ShowActivitiesActivity
            }
        });

        // Button to view feedback
        viewFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewFeedbackIntent = new Intent(StudentDashboardActivity.this, ViewFeedbackActivity.class);
                startActivity(viewFeedbackIntent); // Navigate to FeedbackViewActivity
            }
        });
    }
}


