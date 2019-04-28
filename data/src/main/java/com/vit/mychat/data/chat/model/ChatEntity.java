package com.vit.mychat.data.chat.model;

import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.data.message.model.MessageEntity;
import com.vit.mychat.data.user.model.UserEntity;

public class ChatEntity {

    private UserEntity user;

    private GroupEntity group;

    private MessageEntity lastMessage;

    public ChatEntity() {
    }

    public ChatEntity(UserEntity user, GroupEntity group, MessageEntity lastMessage) {
        this.user = user;
        this.group = group;
        this.lastMessage = lastMessage;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public MessageEntity getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageEntity lastMessage) {
        this.lastMessage = lastMessage;
    }
}
