package com.java.chengsixiang.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_HISTORY = "history";
    private static final String TABLE_NAME_FAVORTIE = "history";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_READ_DATE = "readDate";
    private static final String COLUMN_STAR_DATE = "starDate";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_NEWS_ID = "newsID";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_VIDEO = "video";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_READ_DATE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_NEWS_ID + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_VIDEO + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时的操作，如果不需要可以为空
    }
}
