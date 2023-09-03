package com.java.chengsixiang;

import android.app.Application;

import com.java.chengsixiang.utils.DatabaseManager;

public class EtherealApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.getDatabase(this);
    }
}
