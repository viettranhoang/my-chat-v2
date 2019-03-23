package com.vit.mychat.ui.chat;

public class Chat {
    private String name;
    private String avatar;
    private boolean online;
    private String timeSeen;
    private String lastMessage;
    private boolean seen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getTimeSeen() {
        return timeSeen;
    }

    public void setTimeSeen(String timeSeen) {
        this.timeSeen = timeSeen;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Chat(String name, String avatar, boolean online, String timeSeen, String lastMessage, boolean seen) {

        this.name = name;
        this.avatar = avatar;
        this.online = online;
        this.timeSeen = timeSeen;
        this.lastMessage = lastMessage;
        this.seen = seen;
    }
}
