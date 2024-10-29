// ManageClassesActivity.java
package com.example.appk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ManageClassesActivity extends AppCompatActivity {

    private ListView classesListView;
    private Button addClassButton;
    private EditText classNameEditText;
    private ClassAdapter classAdapter;
    private ArrayList<String> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        // Initialize the UI components
        classesListView = findViewById(R.id.classesListView);
        addClassButton = findViewById(R.id.addClassButton);
        classNameEditText = findViewById(R.id.classNameEditText);

        // Initialize the class list
        classList = new ArrayList<>();

        // Initialize adapter for ListView
        classAdapter = new ClassAdapter(this, classList);
        classesListView.setAdapter(classAdapter);

        // Button to add new classes
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameEditText.getText().toString().trim();
                if (!className.isEmpty()) {
                    classList.add(className);
                    classAdapter.notifyDataSetChanged();  // Refresh ListView
                    classNameEditText.setText("");  // Clear the input field
                } else {
                    Toast.makeText(ManageClassesActivity.this, "Please enter a class name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Long-click to remove class (for example)
        classesListView.setOnItemLongClickListener((parent, view, position, id) -> {
            String removedClass = classList.remove(position);
            classAdapter.notifyDataSetChanged();
            Toast.makeText(ManageClassesActivity.this, removedClass + " removed", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
