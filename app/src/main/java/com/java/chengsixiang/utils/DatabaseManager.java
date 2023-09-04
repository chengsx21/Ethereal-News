package com.java.chengsixiang.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static SQLiteDatabase db;

    public static synchronized void getDatabase(Context context) {
        if (db == null) {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
        }
    }
}
