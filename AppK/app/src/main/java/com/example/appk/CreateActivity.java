package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    private EditText activityNameEditText, activityDescriptionEditText;
    private Button createActivityButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);  // Link the XML layout

        // Initialize database helper
        db = new DatabaseHelper(this);

        // Link XML views to Java variables
        activityNameEditText = findViewById(R.id.activityNameEditText);
        activityDescriptionEditText = findViewById(R.id.activityDescriptionEditText);
        createActivityButton = findViewById(R.id.createActivityButton);

        // Set click listener for the Create button
        createActivityButton.setOnClickListener(v -> {
            String activity_name = activityNameEditText.getText().toString();
            String description = activityDescriptionEditText.getText().toString();

            // Validate that both fields are not empty
            if (activity_name.isEmpty() || description.isEmpty()) {
                Toast.makeText(CreateActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Add the activity to the database
                boolean isInserted = db.addProgram(activity_name, description);

                if (isInserted) {
                    Toast.makeText(CreateActivity.this, "Activity Created Successfully", Toast.LENGTH_SHORT).show();

                    // Redirect back to Teacher Dashboard (or wherever)
                    Intent intent = new Intent(CreateActivity.this, TeacherDashboardActivity.class);
                    startActivity(intent);
                    finish();  // Finish this activity so it's removed from the back stack
                } else {
                    Toast.makeText(CreateActivity.this, "Failed to Create Activity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
