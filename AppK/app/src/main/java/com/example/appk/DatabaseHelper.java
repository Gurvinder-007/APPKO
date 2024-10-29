package com.example.appk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "komodo.db";
    private static final int DATABASE_VERSION = 3;

    // Table and column names for Users table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    // Table and column names for Programs table
    public static final String TABLE_PROGRAMS = "programs";
    public static final String COLUMN_PROGRAM_ID = "program_id";
    public static final String COLUMN_PROGRAM_NAME = "program_name";
    public static final String COLUMN_PROGRAM_DESCRIPTION = "program_description";

    // Table and column names for Submissions table
    public static final String TABLE_SUBMISSIONS = "submissions";

    //public static final String COLUMN_SUBMISSION_ID = "submission_id";
    public static final String COLUMN_SUBMISSION_USER_ID = "submission_user_id";
    public static final String COLUMN_SUBMISSION_PROGRAM_ID = "submission_program_id";
    public static final String COLUMN_SUBMISSION_DETAILS = "submission_details";

    public static final String COLUMN_SUBMISSION_IMAGE = "submission_image";


    // Table and column names for Feedback table
    public static final String TABLE_FEEDBACK = "feedback";
    public static final String COLUMN_FEEDBACK_ID = "feedback_id"; // Primary key
    public static final String COLUMN_FEEDBACK_STUDENT_NAME = "student_name"; // Student name
    public static final String COLUMN_FEEDBACK_TEXT = "feedback_text"; // Feedback text




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Add a new user to the database
    public boolean addUser(String name, String email, String hashedPassword, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, hashedPassword);
        contentValues.put(COLUMN_ROLE, role);  // Add role (e.g., teacher/student)

        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;  // Return true if insertion was successful
    }

    // Validate user credentials during login
    public boolean validateUser(String username, String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, hashedPassword});

        boolean isValid = (cursor != null && cursor.moveToFirst());  // Ensure that there is a result and move to first record

        if (cursor != null) {
            cursor.close();
        }

        return isValid;
    }

    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROLE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + "=?",
                new String[]{username});

        String role = null;
        if (cursor != null && cursor.moveToFirst()) {
            role = cursor.getString(0);  // Get the role from the first column
            cursor.close();
        }

        return role;
    }

    // Table and column names for Tasks table (NEW)
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_PROGRAM_ID = "task_program_id";
    public static final String COLUMN_TASK_DESCRIPTION = "task_description";

    // Table and column names for feedback in Submissions table (NEW)
    public static final String COLUMN_FEEDBACK = "feedback";

    // Table for assigning users (students) to programs (NEW)
    public static final String TABLE_PROGRAM_USERS = "program_users";
    public static final String COLUMN_PROGRAM_USER_ID = "program_user_id"; // Primary key
    public static final String COLUMN_PROGRAM_USER_PROGRAM_ID = "program_id";
    public static final String COLUMN_PROGRAM_USER_USER_ID = "user_id";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PROGRAMS_TABLE = "CREATE TABLE " + TABLE_PROGRAMS + "("
                + COLUMN_PROGRAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROGRAM_NAME + " TEXT,"
                + COLUMN_PROGRAM_DESCRIPTION + " TEXT"
                + ")";
        db.execSQL(CREATE_PROGRAMS_TABLE);

        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + "("
                + COLUMN_FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FEEDBACK_STUDENT_NAME + " TEXT,"
                + COLUMN_FEEDBACK_TEXT + " TEXT"
                + ")";
        db.execSQL(CREATE_FEEDBACK_TABLE);

        // Create Tasks table (NEW)
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_NAME + " TEXT,"
                + COLUMN_TASK_PROGRAM_ID + " INTEGER,"
                + COLUMN_TASK_DESCRIPTION + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_TASK_PROGRAM_ID + ") REFERENCES " + TABLE_PROGRAMS + "(" + COLUMN_PROGRAM_ID + ")"
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        // Create Program_Users table (NEW)
        String CREATE_PROGRAM_USERS_TABLE = "CREATE TABLE " + TABLE_PROGRAM_USERS + "("
                + COLUMN_PROGRAM_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROGRAM_USER_PROGRAM_ID + " INTEGER,"
                + COLUMN_PROGRAM_USER_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_PROGRAM_USER_PROGRAM_ID + ") REFERENCES " + TABLE_PROGRAMS + "(" + COLUMN_PROGRAM_ID + "),"
                + "FOREIGN KEY(" + COLUMN_PROGRAM_USER_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_PROGRAM_USERS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);



        // Create Submissions table with the new column
        String CREATE_SUBMISSIONS_TABLE = "CREATE TABLE " + TABLE_SUBMISSIONS + "("
                 //COLUMN_SUBMISSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SUBMISSION_USER_ID + " INTEGER,"
                + COLUMN_SUBMISSION_PROGRAM_ID + " INTEGER,"
                + COLUMN_SUBMISSION_DETAILS + " TEXT,"
                + COLUMN_SUBMISSION_IMAGE + " BLOB," // Add image column as BLOB
                + "FOREIGN KEY(" + COLUMN_SUBMISSION_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
                + "FOREIGN KEY(" + COLUMN_SUBMISSION_PROGRAM_ID + ") REFERENCES " + TABLE_PROGRAMS + "(" + COLUMN_PROGRAM_ID + ")"
                + ")";
        db.execSQL(CREATE_SUBMISSIONS_TABLE);
    }

    public List<Submission> getAllSubmissions() {
        List<Submission> submissionsList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.d("DatabaseHelper", "Opening database to retrieve submissions");

        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM submissions";
            cursor = db.rawQuery(query, null);

            Log.d("DatabaseHelper", "Query executed: " + query);

            if (cursor.moveToFirst()) {
                Log.d("DatabaseHelper", "Submissions found in cursor");
                do {
                   // int id = cursor.getInt(cursor.getColumnIndexOrThrow("submission_id"));
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow("submission_user_id"));
                    int programId = cursor.getInt(cursor.getColumnIndexOrThrow("submission_program_id"));
                    String details = cursor.getString(cursor.getColumnIndexOrThrow("submission_details"));
                    byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("submission_image"));

                    Submission submission = new Submission(userId, programId, details, image);
                    submissionsList.add(submission);

                    Log.d("DatabaseHelper", "Retrieved Submission: " + submission.toString());
                } while (cursor.moveToNext());
            } else {
                Log.d("DatabaseHelper", "No submissions found in the database.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving submissions: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.d("DatabaseHelper", "Database closed after retrieving submissions.");
        }

        return submissionsList;
    }


    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROGRAMS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROGRAM_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROGRAM_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROGRAM_DESCRIPTION));

                Program program = new Program(id, name, description);
                programList.add(program);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return programList;
    }

    // Insert a new task into the tasks table
    public long insertTask(String taskName, String taskDescription, int programId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);
        values.put(COLUMN_TASK_PROGRAM_ID, programId); // Associate with program
        return db.insert(TABLE_TASKS, null, values);
    }

    // Retrieve program details by name
    public Cursor getProgramByName(String programName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PROGRAMS + " WHERE " + COLUMN_PROGRAM_NAME + " = ?", new String[]{programName});
    }

    // Retrieve program ID by name
    public int getProgramIdByName(String programName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PROGRAM_ID + " FROM " + TABLE_PROGRAMS + " WHERE " + COLUMN_PROGRAM_NAME + " = ?", new String[]{programName});

        int programId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            programId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROGRAM_ID));
            cursor.close();
        }
        return programId;
    }

    // Retrieve tasks for a specific program
    public Cursor getTasksForProgram(int programId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASK_PROGRAM_ID + " = ?", new String[]{String.valueOf(programId)});
    }


    public ArrayList<Student> getAllStudentsWithRole(String role) {
        ArrayList<Student> studentsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // SQL query to get all users with the given role
            String query = "SELECT * FROM users WHERE role = ?";
            cursor = db.rawQuery(query, new String[]{role});

            // Iterate through the result and create Student objects
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                    // Assuming the Student class has a constructor that accepts id, name, and email
                    Student student = new Student(name, email);
                    studentsList.add(student);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return studentsList;
    }


    public void submitWork(String details, byte[] imageData) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Retrieve user ID based on username
   //     Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + "=?", new String[]{username});
   //     int userId = -1;
     //   if (cursor.moveToFirst()) {
    //        userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
     //   }
    //    cursor.close();

        // Insert submission details along with the image
        ContentValues values = new ContentValues();
      //  values.put(COLUMN_SUBMISSION_USER_ID, userId);

        values.put(COLUMN_SUBMISSION_DETAILS, details);
        values.put(COLUMN_SUBMISSION_IMAGE, imageData); // Store image data here

        db.insert(TABLE_SUBMISSIONS, null, values);
        db.close();
    }



    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + "=?", new String[]{username});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }
        cursor.close();
        return userId;
    }

    public boolean addProgram(String activity_name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROGRAM_NAME, activity_name);
        contentValues.put(COLUMN_PROGRAM_DESCRIPTION, description);

        long result = db.insert(TABLE_PROGRAMS, null, contentValues);
        db.close();
        return result != -1;  // Return true if insertion was successful
    }


    // Method to add feedback
    public boolean addFeedback(String studentName, String feedbackText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FEEDBACK_STUDENT_NAME, studentName);
        values.put(COLUMN_FEEDBACK_TEXT, feedbackText);

        long result = db.insert(TABLE_FEEDBACK, null, values);
        db.close();
        return result != -1; // Return true if insertion was successful
    }

    // Method to retrieve all feedback
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FEEDBACK, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEEDBACK_ID));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEEDBACK_STUDENT_NAME));
                String feedbackText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FEEDBACK_TEXT));

                Feedback feedback = new Feedback(id, studentName, feedbackText);
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return feedbackList;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBMISSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);

        onCreate(db);
    }
}

