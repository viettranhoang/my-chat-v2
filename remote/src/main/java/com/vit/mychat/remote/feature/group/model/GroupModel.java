package com.vit.mychat.remote.feature.group.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("avatar", avatar);

        HashMap<String, Object> membersMap = new HashMap<>();
        for (String s : members) {
            membersMap.put(s, "");
        }

        result.put("members", membersMap);

        return result;
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
