package com.example.appk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "komodo.db";
    private static final int DATABASE_VERSION = 1;

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
    public static final String COLUMN_SUBMISSION_ID = "submission_id";
    public static final String COLUMN_SUBMISSION_USER_ID = "submission_user_id";
    public static final String COLUMN_SUBMISSION_PROGRAM_ID = "submission_program_id";
    public static final String COLUMN_SUBMISSION_DETAILS = "submission_details";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean addUser(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("role", role);

        long result = db.insert("Users", null, contentValues);
        db.close();

        return result != -1; // Return true if insert is successful
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Programs table
        String CREATE_PROGRAMS_TABLE = "CREATE TABLE " + TABLE_PROGRAMS + "("
                + COLUMN_PROGRAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PROGRAM_NAME + " TEXT,"
                + COLUMN_PROGRAM_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_PROGRAMS_TABLE);

        // Create Submissions table
        String CREATE_SUBMISSIONS_TABLE = "CREATE TABLE " + TABLE_SUBMISSIONS + "("
                + COLUMN_SUBMISSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SUBMISSION_USER_ID + " INTEGER,"
                + COLUMN_SUBMISSION_PROGRAM_ID + " INTEGER,"
                + COLUMN_SUBMISSION_DETAILS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_SUBMISSION_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
                + "FOREIGN KEY(" + COLUMN_SUBMISSION_PROGRAM_ID + ") REFERENCES " + TABLE_PROGRAMS + "(" + COLUMN_PROGRAM_ID + ")" + ")";
        db.execSQL(CREATE_SUBMISSIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBMISSIONS);
        onCreate(db);
    }
}

