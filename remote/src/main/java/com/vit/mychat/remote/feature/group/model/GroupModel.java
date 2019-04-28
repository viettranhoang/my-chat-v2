package com.vit.mychat.remote.feature.group.model;

import java.util.List;

public class GroupModel {

    private String id;
    private String name;
    private String avatar;
    private List<String> members;

    public GroupModel() {
    }

    public GroupModel(String id, String name, String avatar, List<String> members) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.members = members;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
