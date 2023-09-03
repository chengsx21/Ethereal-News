package com.java.chengsixiang.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    public static synchronized SQLiteDatabase getDatabase(Context context) {
        if (db == null) {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }
}
