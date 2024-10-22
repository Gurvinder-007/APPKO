package com.example.appk;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    TextView studentNameTextView, programNameTextView, taskCompletionTextView;
    ImageView avatarImageView;
    ListView programListView;
    Button uploadSightingButton, completeTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // Initialize UI elements
        studentNameTextView = findViewById(R.id.studentNameTextView);
        programNameTextView = findViewById(R.id.programNameTextView);
        taskCompletionTextView = findViewById(R.id.taskCompletionTextView);
        avatarImageView = findViewById(R.id.avatarImageView);
        programListView = findViewById(R.id.programListView);
        uploadSightingButton = findViewById(R.id.uploadSightingButton);
        completeTaskButton = findViewById(R.id.completeTaskButton);

        // Set up listeners
    //    uploadSightingButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
        //    public void onClick(View view) {
          //      Intent intent = new Intent(StudentDashboardActivity.this, UploadSightingActivity.class);
            //    startActivity(intent);
           // }
       // });

      //  completeTaskButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
          //  public void onClick(View view) {
            //    Intent intent = new Intent(StudentDashboardActivity.this, CompleteTaskActivity.class);
              //  startActivity(intent);
          //  }
       // });

        // You can load data such as student's program and progress dynamically from the database.
    }
}

