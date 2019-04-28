package com.vit.mychat.data.chat.mapper;


import com.vit.mychat.data.Mapper;
import com.vit.mychat.data.chat.model.ChatEntity;
import com.vit.mychat.data.group.mapper.GroupEntityMapper;
import com.vit.mychat.data.message.mapper.MessageEntityMapper;
import com.vit.mychat.data.user.mapper.UserEntityMapper;
import com.vit.mychat.domain.usecase.chat.model.Chat;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatEntityMapper implements Mapper<ChatEntity, Chat> {

    @Inject
    UserEntityMapper userEntityMapper;

    @Inject
    GroupEntityMapper groupEntityMapper;

    @Inject
    MessageEntityMapper messageEntityMapper;

    @Inject
    public ChatEntityMapper() {
    }

    @Override
    public ChatEntity mapToEntity(Chat type) {
        if (type == null) {
            return null;
        }
        return new ChatEntity(userEntityMapper.mapToEntity(type.getUser()),
                groupEntityMapper.mapToEntity(type.getGroup()),
                messageEntityMapper.mapToEntity(type.getLastMessage()));
    }

    @Override
    public Chat mapFromEntity(ChatEntity type) {
        if (type == null) {
            return null;
        }
        return new Chat(userEntityMapper.mapFromEntity(type.getUser()),
                groupEntityMapper.mapFromEntity(type.getGroup()),
                messageEntityMapper.mapFromEntity(type.getLastMessage()));
    }
}
