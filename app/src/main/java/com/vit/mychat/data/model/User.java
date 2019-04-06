package com.vit.mychat.data.model;

public class User {
    private String name;
    private String status;
    private String avatar;
    private String cover;
    private String news;
    private long online;

    public User(String name, String status, String avatar, String cover, String news, long online) {
        this.name = name;
        this.status = status;
        this.avatar = avatar;
        this.cover = cover;
        this.news = news;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCover() {
        return cover;
    }

    public String getNews() {
        return news;
    }

    public long getOnline() {
        return online;
    }
}
