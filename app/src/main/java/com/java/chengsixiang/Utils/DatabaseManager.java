package com.java.chengsixiang.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseManager {
    private static SQLiteDatabase db;

    public static synchronized void getDatabase(Context context) {
        if (db == null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                db = dbHelper.getWritableDatabase();
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }
}
