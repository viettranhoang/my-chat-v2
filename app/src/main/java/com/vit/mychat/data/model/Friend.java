package com.vit.mychat.data.model;

public class Friend {
    private String avatar;
    private  String name;
    private boolean online;
    private  String news;

    public Friend(String avatar, String name, boolean online, String news) {
        this.avatar = avatar;
        this.name = name;
        this.online = online;
        this.news = news;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
