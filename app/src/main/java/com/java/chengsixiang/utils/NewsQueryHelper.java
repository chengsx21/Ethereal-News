package com.java.chengsixiang.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsQueryHelper {
    private final OkHttpClient client = new OkHttpClient();

    public void queryNews(String size, String startDate, String endDate, String words, String categories) {
        String apiUrl = String.format("https://api2.newsminer.net/svc/news/queryNewsList?size=%s&startDate=%s&endDate=%s&words=%s&categories=%s", size, startDate, endDate, words, categories);
        Log.d("NewsQueryHelper", apiUrl);
        Request request = new Request.Builder().url(apiUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String resData = response.body().string();
                Log.d("NewsQueryHelper", resData);
                // you may try obj =  Gson().fromJson(responseData, yourClass.class);
            }
            else {
                Log.d("NewsQueryHelper", "Failed to get news data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
