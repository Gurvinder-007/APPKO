package com.example.appk;

public class Feedback {
    private int id;
    private String studentName;
    private String feedbackText;

    public Feedback(int id, String studentName, String feedbackText) {
        this.id = id;
        this.studentName = studentName;
        this.feedbackText = feedbackText;
    }

    @Override
    public String toString() {
        return studentName + ": " + feedbackText; // How you want to display feedback
    }

    // Getters and setters can be added if needed
}

