package com.java.chengsixiang.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

    public long insertRecord(String tableName, String columnDate, String title, String author, String date, String currentDate, String content, String newsID, String imageUrl, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_DATE, date);
        values.put(columnDate, currentDate);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_NEWS_ID, newsID);
        values.put(COLUMN_IMAGE, imageUrl);
        values.put(COLUMN_VIDEO, videoUrl);
        long rowId = db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return rowId;
    }

    @SuppressLint("Range")
    public List<NewsItem> getRecord(String tableName, String columnDate) {
        List<NewsItem> newsItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_AUTHOR,
                COLUMN_DATE,
                columnDate,
                COLUMN_CONTENT,
                COLUMN_NEWS_ID,
                COLUMN_IMAGE,
                COLUMN_VIDEO
        };

        Cursor cursor = db.query(tableName, columns, null, null, null, null, columnDate + " DESC");
        if (cursor.moveToFirst()) {
            do {
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                newsItem.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                newsItem.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                newsItem.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
                newsItem.setNewsID(cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_ID)));
                newsItem.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                newsItem.setVideoUrl(cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO)));
                newsItems.add(newsItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return newsItems;
    }

    public boolean isNewsIDExists(String tableName, String newsID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE newsID = ?";
        Cursor cursor = db.rawQuery(query, new String[] { newsID });
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst())
                count = cursor.getInt(0);
            cursor.close();
        }
        return count > 0;
    }

    public long insertHistoryRecord(String title, String author, String date, String readDate, String content, String newsID, String imageUrl, String videoUrl) {
        return insertRecord(TABLE_NAME_HISTORY, COLUMN_READ_DATE, title, author, date, readDate, content, newsID, imageUrl, videoUrl);
    }

    public long insertFavoriteRecord(String title, String author, String date, String starDate, String content, String newsID, String imageUrl, String videoUrl) {
        return insertRecord(TABLE_NAME_FAVORTIE, COLUMN_STAR_DATE, title, author, date, starDate, content, newsID, imageUrl, videoUrl);
    }

    public long deleteFavoriteRecord(String newsID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = db.delete(TABLE_NAME_FAVORTIE, COLUMN_NEWS_ID + " = ?", new String[] { newsID });
        db.close();
        return rowId;
    }

    public List<NewsItem> getHistoryRecord() {
        return getRecord(TABLE_NAME_HISTORY, COLUMN_READ_DATE);
    }

    public List<NewsItem> getFavoriteRecord() {
        return getRecord(TABLE_NAME_FAVORTIE, COLUMN_STAR_DATE);
    }

    public boolean isNewsIDExistsInHistory(String newsID) {
        return isNewsIDExists(TABLE_NAME_HISTORY, newsID);
    }

    public boolean isNewsIDExistsInFavorite(String newsID) {
        return isNewsIDExists(TABLE_NAME_FAVORTIE, newsID);
    }
}
