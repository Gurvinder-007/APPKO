package com.example.appk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private EditText studentNameEditText;
    private EditText feedbackEditText;
    private Button submitFeedbackButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        studentNameEditText = findViewById(R.id.student_name);
        feedbackEditText = findViewById(R.id.feedback_text);
        submitFeedbackButton = findViewById(R.id.submit_feedback_button);
        db = new DatabaseHelper(this);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName = studentNameEditText.getText().toString().trim();
                String feedbackText = feedbackEditText.getText().toString().trim();

                if (!studentName.isEmpty() && !feedbackText.isEmpty()) {
                    if (db.addFeedback(studentName, feedbackText)) {
                        Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FeedbackActivity.this, "Failed to submit feedback!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FeedbackActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

