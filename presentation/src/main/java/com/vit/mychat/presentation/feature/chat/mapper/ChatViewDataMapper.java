package com.vit.mychat.presentation.feature.chat.mapper;


import com.vit.mychat.domain.usecase.chat.model.Chat;
import com.vit.mychat.presentation.Mapper;
import com.vit.mychat.presentation.feature.chat.model.ChatViewData;
import com.vit.mychat.presentation.feature.group.mapper.GroupViewDataMapper;
import com.vit.mychat.presentation.feature.message.mapper.MessageViewDataMapper;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatViewDataMapper implements Mapper<Chat, ChatViewData> {

    @Inject
    UserViewDataMapper userViewDataMapper;

    @Inject
    GroupViewDataMapper groupViewDataMapper;

    @Inject
    MessageViewDataMapper messageViewDataMapper;

    @Inject
    public ChatViewDataMapper() {
    }

    @Override
    public ChatViewData mapToViewData(Chat type) {
        if (type == null) {
            return null;
        }
        return new ChatViewData(userViewDataMapper.mapToViewData(type.getUser()),
                groupViewDataMapper.mapToViewData(type.getGroup()),
                messageViewDataMapper.mapToViewData(type.getLastMessage()));
    }

    @Override
    public Chat mapFromViewData(ChatViewData type) {
        if (type == null) {
            return null;
        }
        return new Chat(userViewDataMapper.mapFromViewData(type.getUser()),
                groupViewDataMapper.mapFromViewData(type.getGroup()),
                messageViewDataMapper.mapFromViewData(type.getLastMessage()));
    }
}
