package com.vit.mychat.data.user.model;

public class UserEntity {

    private String id;
    private String name;
    private String status;
    private String avatar;
    private String cover;
    private String news;
    private long online;

    public UserEntity() {
    }

    public UserEntity(String id, String name, String status, String avatar, String cover, String news, long online) {
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
