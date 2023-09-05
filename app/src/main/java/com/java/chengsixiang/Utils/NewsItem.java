package com.java.chengsixiang.Utils;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String title;
    private String content;
    private String date;
    private String author;
    private String imageUrl;
    private String videoUrl;
    private String newsID;

    public NewsItem() {
        this.title = "Come and Enjoy Genshin Impact!";
        this.content = "This is the official community for Genshin Impact!";
        this.date = "2023.8.15";
        this.author = "Genshin Impact";
        this.imageUrl = "https://s2.loli.net/2023/08/15/K54zSXytBfVcnZm.png";
        this.videoUrl = "http://flv3.people.com.cn/dev1/mvideo/vodfiles/2021/09/07/2eb63e58f8c0b6cee1c2c1dde92940ef_c.mp4";
        this.newsID = "202309310128d5295250654d46928c9a7c8de0b340be";
    }

    public NewsItem(String title, String content, String date, String author, String imageUrl, String videoUrl, String newsID) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public void setVideoUrl(String url) {
        this.videoUrl = url;
    }

    public void setNewsID(String id) {
        this.newsID = id;
    }
}
