package com.example.appk;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AssignClassActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private ArrayAdapter<String> adapter;
    private ArrayList<Student> students;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_class);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get the ListView reference from layout
        listViewStudents = findViewById(R.id.listViewStudents); // Assuming you have a ListView for displaying students

        // Fetch the list of students with the role "student"
        students = databaseHelper.getAllStudentsWithRole("student");

        // Create a list of student names for display
        ArrayList<String> studentNames = new ArrayList<>();
        for (Student student : students) {
            studentNames.add(student.getName()); // Assuming the 'Student' class has a getName() method
        }

        // Set up the adapter to display the student names in the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listViewStudents.setAdapter(adapter);

        // Set an item click listener to handle clicks on student names
        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected student from the list
                Student selectedStudent = students.get(position);

                // Display a message with the selected student's name (you can modify this to assign classes)
                Toast.makeText(AssignClassActivity.this, "Assigning class to: " + selectedStudent.getName(), Toast.LENGTH_SHORT).show();

                // Here, you can add logic to assign the selected student to a class
            }
        });
    }
}


