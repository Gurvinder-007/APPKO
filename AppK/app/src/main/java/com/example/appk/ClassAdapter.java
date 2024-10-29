// ClassAdapter.java
package com.example.appk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class ClassAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> classList;

    public ClassAdapter(Context context, ArrayList<String> classList) {
        super(context, 0, classList);
        this.context = context;
        this.classList = classList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate it
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_class, parent, false);
        }

        // Get the data item for this position
        String className = classList.get(position);

        // Lookup view for data population
        TextView classNameTextView = convertView.findViewById(R.id.classNameTextView);

        // Populate the data into the template view using the data object
        classNameTextView.setText(className);

        // Return the completed view to render on screen
        return convertView;
    }
}
