package com.example.appk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KomodoApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "Users";

    public ProfileDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT, role TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Validate user credentials
    public boolean validateUser(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE name = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        //initiate test code to check if it gets this far
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    // Get the role of the user (Student or Teacher)
    public String getRole(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT role FROM " + TABLE_USERS + " WHERE name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0);
        }
        cursor.close();
        return role;
    }
}
