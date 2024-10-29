package com.example.appk;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SubmitWorkActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "SubmitWorkActivity";
    private DatabaseHelper db;
    private EditText submissionText;
    private ImageView submissionImageView;
    private Button selectImageButton, submitButton;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_work);

        db = new DatabaseHelper(this);
        submissionText = findViewById(R.id.submission_text);
        submissionImageView = findViewById(R.id.submission_image_view);
        selectImageButton = findViewById(R.id.select_image_button);
        submitButton = findViewById(R.id.submit_button);

        // Set up button to pick an image
        selectImageButton.setOnClickListener(v -> openImageChooser());

        // Handle submission of work
        submitButton.setOnClickListener(v -> submitWork());

        Log.d(TAG, "SubmitWorkActivity created");
    }

    // Open image chooser for selecting an image from the gallery
    private void openImageChooser() {
        Log.d(TAG, "Opening image chooser");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle result of image chooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            submissionImageView.setImageURI(imageUri); // Display chosen image
            Log.d(TAG, "Image selected: " + imageUri.toString());
        } else {
            Log.w(TAG, "Image selection failed or cancelled");
        }
    }

    private void submitWork() {
        // Retrieve username from the Intent
    //    String username = getIntent().getStringExtra("USERNAME");

        // Log and check if username is null
       // if (username == null) {
         //   Log.e(TAG, "Username is missing! Submission cannot proceed.");
           // return;
    //    } else {
      //      Log.d(TAG, "Received username: " + username);
      //  }

        String submissionDetails = submissionText.getText().toString();

   //     Log.d(TAG, "Submitting work with username: " + username);
        Log.d(TAG, "Submission details: " + submissionDetails);

        if (imageUri != null) {
            try {
                // Convert image URI to byte array
                byte[] imageBytes = getBytesFromUri(imageUri);
                Log.d(TAG, "Image byte array length: " + imageBytes.length);

                db.submitWork(submissionDetails, imageBytes);
                Log.d(TAG, "Submission successful");
            } catch (Exception e) {
                Log.e(TAG, "Error submitting work: " + e.getMessage());
            }

            finish(); // Close activity after submission
        } else {
            Log.w(TAG, "No image selected for submission");
        }
    }

    // Helper method to convert URI to byte array
    private byte[] getBytesFromUri(Uri uri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024; // Set buffer size to 1KB
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}

