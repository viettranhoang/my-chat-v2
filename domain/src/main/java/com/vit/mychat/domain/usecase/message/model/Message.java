package com.vit.mychat.domain.usecase.message.model;

public class Message {

    private String message;
    private String from;
    private boolean seen;
    private long time;
    private String type;
    private String avatar;

    public Message() {
    }

    public Message(String message, String from, boolean seen, long time, String type, String avatar) {
        this.message = message;
        this.from = from;
        this.seen = seen;
        this.time = time;
        this.type = type;
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
