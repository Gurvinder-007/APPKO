package com.example.appk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SubmissionAdapter extends ArrayAdapter<Submission> {

    private Context context;
    private List<Submission> submissions;

    public SubmissionAdapter(Context context, List<Submission> submissions) {
        super(context, 0, submissions);
        this.context = context;
        this.submissions = submissions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.submission_item, parent, false);
        }

        Submission submission = submissions.get(position);
        TextView detailsTextView = convertView.findViewById(R.id.submission_details);
        ImageView imageView = convertView.findViewById(R.id.submission_image);

        // Set the details text
        detailsTextView.setText(submission.getDetails());

        // Check if the image exists
        byte[] imageBytes = submission.getImage();
        Bitmap bitmap = null;

        if (imageBytes != null && imageBytes.length > 0) {
            Log.d("SubmissionAdapter", "Image byte array length: " + imageBytes.length);
            bitmap = decodeSampledBitmapFromByteArray(imageBytes, 100, 100); // Example dimensions
        }

        // Set the Bitmap to the ImageView, or a placeholder if decoding failed
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.placeholder_image12); // Placeholder if no image
        }

        return convertView;
    }

    // Helper method to decode the byte array into a scaled Bitmap
    private Bitmap decodeSampledBitmapFromByteArray(byte[] imageBytes, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // Get dimensions without loading bitmap
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false; // Now decode with inSampleSize set

        // Decode bitmap with inSampleSize set
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
    }

    // Method to calculate inSampleSize
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

