package com.example.appk;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowActivitiesActivity extends AppCompatActivity {

    private ListView activitiesListView;
    private DatabaseHelper db;
    private List<Program> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);

        activitiesListView = findViewById(R.id.activities_list_view);
        db = new DatabaseHelper(this);
        activities = new ArrayList<>();

        // Load activities from the database
        loadActivities();

        // Set up the adapter to display activities
        ArrayAdapter<Program> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        activitiesListView.setAdapter(adapter);

        // Set up an item click listener for the list
        activitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Program selectedActivity = activities.get(position);
                Toast.makeText(ShowActivitiesActivity.this, "Selected: " + selectedActivity.getName(), Toast.LENGTH_SHORT).show();
                // You can add functionality here to show more details about the selected activity if needed
            }
        });
    }

    // Load activities from the database
    private void loadActivities() {
        activities.clear(); // Clear any existing activities
        List<Program> programList = db.getAllPrograms(); // Get all programs from the database

        activities.addAll(programList); // Add retrieved programs to the list
    }
}

