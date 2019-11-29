package com.vit.mychat.presentation.feature.chat.model;

import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

public class ChatViewData {

    private UserViewData user;

    private GroupViewData group;

    private MessageViewData lastMessage;

    public ChatViewData() {
    }

    public ChatViewData(UserViewData user, GroupViewData group, MessageViewData lastMessage) {
        this.user = user;
        this.group = group;
        this.lastMessage = lastMessage;
    }

    public UserViewData getUser() {
        return user;
    }

    public void setUser(UserViewData user) {
        this.user = user;
    }

    public GroupViewData getGroup() {
        return group;
    }

    public void setGroup(GroupViewData group) {
        this.group = group;
    }

    public MessageViewData getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageViewData lastMessage) {
        this.lastMessage = lastMessage;
    }

    public interface OnClickChatItemListener {
        void onClickUserChatItem(UserViewData userViewData);
    }
}
