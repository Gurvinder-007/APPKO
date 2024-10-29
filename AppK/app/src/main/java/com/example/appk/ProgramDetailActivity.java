package com.example.appk;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProgramDetailActivity extends AppCompatActivity {

    private TextView programNameTextView, programDescriptionTextView;
    private ListView taskListView;
    private DatabaseHelper dbHelper;
    private List<String> taskList;
    private ArrayAdapter<String> taskAdapter;
    private String programName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        // Initialize database helper and UI components
        dbHelper = new DatabaseHelper(this);
        programNameTextView = findViewById(R.id.programNameTextView);
        programDescriptionTextView = findViewById(R.id.programDescriptionTextView);
        taskListView = findViewById(R.id.taskListView);

        // Get the selected program name from the intent
        Intent intent = getIntent();
        programName = intent.getStringExtra("program_name");

        if (programName == null) {
            Toast.makeText(this, "No program selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display the program name
        programNameTextView.setText(programName);

        // Load the program details
        loadProgramDetails();

        // Load the tasks associated with the program
        loadTasksForProgram();
    }

    private void loadProgramDetails() {
        // Get the program details from the database
        Cursor cursor = dbHelper.getProgramByName(programName);

        if (cursor != null && cursor.moveToFirst()) {
            // Display program description
            String programDescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROGRAM_DESCRIPTION));
            programDescriptionTextView.setText(programDescription);

            cursor.close();
        } else {
            Toast.makeText(this, "Program details not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTasksForProgram() {
        // Get the program ID from the program name
        int programId = dbHelper.getProgramIdByName(programName);

        // Retrieve tasks for the program
        Cursor cursor = dbHelper.getTasksForProgram(programId);

        // Initialize the task list
        taskList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Add each task's name to the task list
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_NAME));
                taskList.add(taskName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // If no tasks found, notify the user
        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks found for this program", Toast.LENGTH_SHORT).show();
        }

        // Set the adapter for the ListView to display tasks
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);
    }
}
