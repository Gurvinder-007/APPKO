package com.example.appk;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

    private ListView feedbackListView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        feedbackListView = findViewById(R.id.feedback_list_view);
        db = new DatabaseHelper(this);

        // Load feedback from database and set to ListView
        loadFeedback();
    }

    private void loadFeedback() {
        List<Feedback> feedbackList = db.getAllFeedback();
        ArrayAdapter<Feedback> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, feedbackList);
        feedbackListView.setAdapter(adapter);
    }
}
