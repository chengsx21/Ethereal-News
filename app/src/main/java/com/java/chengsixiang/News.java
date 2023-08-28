package com.java.chengsixiang;

public class News {
    String title;
    String content;
    String date;
    String author;
    String url;

    public News() {
        this.title = "Fake News";
        this.content = "Fake content";
        this.date = "Fake date";
        this.author = "Fake author";
        this.url = "https://s2.loli.net/2023/08/15/K54zSXytBfVcnZm.png";
    }

    public News(String title, String content, String date, String author, String url) {
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

    public String toHtml() {
        return "<h1>" + title + "</h1>" +
                "<p>" + content + "</p>" +
                "<p>" + date + "</p>" +
                "<p>" + author + "</p>" +
                "<p>" + url + "</p>";
    }
}
