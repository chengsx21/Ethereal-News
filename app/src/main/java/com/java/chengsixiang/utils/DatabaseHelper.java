package com.java.chengsixiang.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "newsDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_HISTORY = "history";
    private static final String TABLE_NAME_FAVORTIE = "favorite";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_READ_DATE = "readDate";
    private static final String COLUMN_STAR_DATE = "starDate";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_NEWS_ID = "newsID";
    private static final String COLUMN_IMAGE = "imageUrl";
    private static final String COLUMN_VIDEO = "videoUrl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHistoryTable = "CREATE TABLE " + TABLE_NAME_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_READ_DATE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_NEWS_ID + " TEXT UNIQUE, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_VIDEO + " TEXT)";
        db.execSQL(createHistoryTable);

        String createFavoriteTable = "CREATE TABLE " + TABLE_NAME_FAVORTIE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_STAR_DATE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_NEWS_ID + " TEXT UNIQUE, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_VIDEO + " TEXT)";
        db.execSQL(createFavoriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public long insertHistoryRecord(String title, String author, String date, String readDate, String content, String newsID, String imageUrl, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_READ_DATE, readDate);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_NEWS_ID, newsID);
        values.put(COLUMN_IMAGE, imageUrl);
        values.put(COLUMN_VIDEO, videoUrl);
        long rowId = db.insertWithOnConflict(TABLE_NAME_HISTORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return rowId;
    }

    public long insertFavoriteRecord(String title, String author, String date, String starDate, String content, String newsID, String imageUrl, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_STAR_DATE, starDate);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_NEWS_ID, newsID);
        values.put(COLUMN_IMAGE, imageUrl);
        values.put(COLUMN_VIDEO, videoUrl);
        long rowId = db.insertWithOnConflict(TABLE_NAME_FAVORTIE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return rowId;
    }

}
