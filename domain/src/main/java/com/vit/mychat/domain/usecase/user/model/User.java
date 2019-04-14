package com.vit.mychat.domain.usecase.user.model;

public class User {
    private String id;
    private String name;
    private String status;
    private String avatar;
    private String cover;
    private String news;
    private long online;

    public User() {
    }

    public User(String id, String name, String status, String avatar, String cover, String news, long online) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.avatar = avatar;
        this.cover = cover;
        this.news = news;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public long getOnline() {
        return online;
    }

    public void setOnline(long online) {
        this.online = online;
    }
}
