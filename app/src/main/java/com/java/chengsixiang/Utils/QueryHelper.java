package com.java.chengsixiang.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class QueryHelper {
    private final OkHttpClient client = new OkHttpClient();
    public interface NewsQueryCallback {
        void onSuccess(List<NewsItem> newsItems, int newsCount);
        void onFailure(String errorMessage);
    }

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public static String getTimeBefore(String currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(currentTime, formatter);
        LocalDateTime newDateTime = dateTime.minusSeconds(1);
        return newDateTime.format(formatter);
    }

    public void queryNews(String size, String startDate, String endDate, String words, String categories, NewsQueryCallback callback) {
        if (Objects.equals(endDate, ""))
            endDate = getCurrentTime();
        if (Objects.equals(categories, "全部"))
            categories = "";

        String apiUrl = String.format("https://api2.newsminer.net/svc/news/queryNewsList?size=%s&startDate=%s&endDate=%s&words=%s&categories=%s", size, startDate, endDate, words, categories);
        Request request = new Request.Builder().url(apiUrl).build();
        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String resData = Objects.requireNonNull(response.body()).string();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(resData).getAsJsonObject();
                    int newsCount = jsonObject.get("total").getAsInt();
                    JsonArray dataArray = jsonObject.getAsJsonArray("data");

                    List<NewsItem> newsItems = new ArrayList<>();
                    for (int i = 0; i < dataArray.size(); i++) {
                        JsonObject newsObject = dataArray.get(i).getAsJsonObject();
                        String title = newsObject.get("title").getAsString();
                        String content = newsObject.get("content").getAsString();
                        String date = newsObject.get("publishTime").getAsString();
                        String author = newsObject.get("publisher").getAsString();
                        String imageUrlArray;
                        JsonElement imageElement = newsObject.get("image");
                        if (imageElement.isJsonArray())
                            imageUrlArray = imageElement.getAsJsonArray().toString();
                        else
                            imageUrlArray = imageElement.getAsString();
                        String imageUrl = decodeImageUrl(imageUrlArray);
                        String videoUrl = newsObject.get("video").getAsString();
                        String newsID = newsObject.get("newsID").getAsString();
                        NewsItem newsItem = new NewsItem(title, content, date, author, imageUrl, videoUrl, newsID);
                        newsItems.add(newsItem);
                    }
                    if (callback != null)
                        callback.onSuccess(newsItems, newsCount);
                } else {
                    if (callback != null)
                        callback.onFailure("Failed to get news data");
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (callback != null)
                    callback.onFailure(e.getMessage());
            }
        }).start();
    }

    private String decodeImageUrl(String imageUrlArray) {
        String imageUrl = "";
        Pattern pattern = Pattern.compile("http[^,\\]]+");
        Matcher matcher = pattern.matcher(imageUrlArray);
        if (matcher.find())
            imageUrl = matcher.group();
        return imageUrl;
    }
}
