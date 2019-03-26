package com.vit.mychat.data.model;

public class Message1 {
    private String avatar;
    private String  content;
    private  boolean  seen;
    private  String time;
    public Message1(String avatar, String content, boolean seen, String time) {
        this.avatar = avatar;
        this.content = content;
        this.seen = seen;
        this.time = time;
    }


    public String getAvatar() {

        return avatar;
    }

    public void setAvatar(String avatar) {

        this.avatar = avatar;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public boolean getSeen()
    {

        return seen;
    }

    public void setSeen(boolean seen) {

        this.seen = seen;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }
}
