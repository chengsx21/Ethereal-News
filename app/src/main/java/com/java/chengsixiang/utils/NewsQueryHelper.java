package com.java.chengsixiang.utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsQueryHelper {
    private final OkHttpClient client = new OkHttpClient();

    public interface NewsQueryCallback {
        void onSuccess(List<NewsItem> newsItems);
        void onFailure(String errorMessage);
    }

    public void queryNews(String size, String startDate, String endDate, String words, String categories, NewsQueryCallback callback) {
        if (Objects.equals(endDate, "")) {
            endDate = getCurrentTime();
        }
        if (Objects.equals(categories, "全部")) {
            categories = "";
        }

        String apiUrl = String.format("https://api2.newsminer.net/svc/news/queryNewsList?size=%s&startDate=%s&endDate=%s&words=%s&categories=%s", size, startDate, endDate, words, categories);
        Log.d("NewsQueryHelper", apiUrl);
        Request request = new Request.Builder().url(apiUrl).build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String resData = response.body().string();
                    Log.d("NewsQueryHelper", resData);

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(resData).getAsJsonObject();
                    JsonArray dataArray = jsonObject.getAsJsonArray("data");

                    List<NewsItem> newsItems = new ArrayList<>();
                    for (int i = 0; i < dataArray.size(); i++) {
                        JsonObject newsObject = dataArray.get(i).getAsJsonObject();
                        String title = newsObject.get("title").getAsString();
                        String content = newsObject.get("content").getAsString();
                        String date = newsObject.get("publishTime").getAsString();
                        String author = newsObject.get("publisher").getAsString();
                        String urlArray = newsObject.get("image").getAsString();
                        String url = "";
                        Pattern pattern = Pattern.compile("http[^,\\]]+");
                        Matcher matcher = pattern.matcher(urlArray);
                        if (matcher.find()) {
                            url = matcher.group();
                            Log.d("NewsQueryHelper", "url:" + url);
                        }
                        NewsItem newsItem = new NewsItem(title, content, date, author, url);
                        newsItems.add(newsItem);
                    }

                    if (callback != null) {
                        callback.onSuccess(newsItems);
                    }
                } else {
                    Log.d("NewsQueryHelper", "Failed to get news data");
                    if (callback != null) {
                        callback.onFailure("Failed to get news data");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }
        }).start();
    }

    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
