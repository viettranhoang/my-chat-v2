package com.vit.mychat.domain.usecase.chat.model;

import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.domain.usecase.message.model.Message;
import com.vit.mychat.domain.usecase.user.model.User;

public class Chat {

    User user;

    Group group;

    Message lastMessage;

    public Chat() {
    }

    public Chat(User user, Group group, Message lastMessage) {
        this.user = user;
        this.group = group;
        this.lastMessage = lastMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
