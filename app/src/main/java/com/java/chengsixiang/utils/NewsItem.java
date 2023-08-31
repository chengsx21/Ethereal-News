package com.java.chengsixiang.utils;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String title;
    private String content;
    private String date;
    private String author;
    private String url;

    public NewsItem() {
        this.title = "Come and Enjoy Genshin Impact!";
        this.content = "This is the official community for Genshin Impact!";
        this.date = "2023.8.15";
        this.author = "Genshin Impact";
        this.url = "https://s2.loli.net/2023/08/15/K54zSXytBfVcnZm.png";
    }

    public NewsItem(String title, String content, String date, String author, String url) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
        this.url = url;
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

    public String getUrl() {
        return url;
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

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
