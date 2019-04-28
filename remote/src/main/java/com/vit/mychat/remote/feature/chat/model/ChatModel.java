package com.vit.mychat.remote.feature.chat.model;

import com.vit.mychat.remote.feature.group.model.GroupModel;
import com.vit.mychat.remote.feature.message.model.MessageModel;
import com.vit.mychat.remote.feature.user.model.UserModel;

public class ChatModel {

    private UserModel user;

    private GroupModel group;

    private MessageModel lastMessage;

    public ChatModel() {
    }

    public ChatModel(UserModel user, GroupModel group, MessageModel lastMessage) {
        this.user = user;
        this.group = group;
        this.lastMessage = lastMessage;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public GroupModel getGroup() {
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public MessageModel getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageModel lastMessage) {
        this.lastMessage = lastMessage;
    }
}
