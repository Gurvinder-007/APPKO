package com.example.appk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Submission {
    private int id;
    private int userId;
    private String details;
    private byte[] image; // Field to store the image as a byte array

    public Submission(int id, int programId, String details, byte[] image) {
        this.id = id;
        this.userId = userId;
        this.details = details;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDetails() {
        return details;
    }

    public byte[] getImage() {
        return image;
    }

    // Optional: Convert byte array to Bitmap for displaying the image
    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}





