package com.example.appk;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ReviewSubmissionActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewSubmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_submissions);

        databaseHelper = new DatabaseHelper(this);
        listViewSubmissions = findViewById(R.id.listViewSubmissions);

        List<Submission> submissionList = databaseHelper.getAllSubmissions();
        SubmissionAdapter adapter = new SubmissionAdapter(this, submissionList);
        listViewSubmissions.setAdapter(adapter);
    }
}






