package com.java.chengsixiang.utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsQueryHelper {
    private final OkHttpClient client = new OkHttpClient();

    public NewsItem[] queryNews(String size, String startDate, String endDate, String words, String categories, Callback callback) {
        String apiUrl = String.format("https://api2.newsminer.net/svc/news/queryNewsList?size=%s&startDate=%s&endDate=%s&words=%s&categories=%s", size, startDate, endDate, words, categories);
        Request request = new Request.Builder().url(apiUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String resData = response.body().string();
                // you may try obj =  Gson().fromJson(responseData, yourClass.class);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
