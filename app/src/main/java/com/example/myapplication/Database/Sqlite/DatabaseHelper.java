package com.example.myapplication.Database.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "DEMO.DB";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_TABLE = "USERS";
    public static final String USER_ID = "ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASSWORD = "PASSWORD";
    private static final String CREATE_DB_QUERY =
            "CREATE TABLE " + DATABASE_TABLE + "(" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT NOT NULL, " + USER_PASSWORD
            + " TEXT NOT NULL " + ");";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }
}
