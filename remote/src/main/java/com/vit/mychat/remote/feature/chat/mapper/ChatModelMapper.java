package com.vit.mychat.remote.feature.chat.mapper;

import com.vit.mychat.data.chat.model.ChatEntity;
import com.vit.mychat.remote.common.Mapper;
import com.vit.mychat.remote.feature.chat.model.ChatModel;
import com.vit.mychat.remote.feature.group.mapper.GroupModelMapper;
import com.vit.mychat.remote.feature.message.mapper.MessageModelMapper;
import com.vit.mychat.remote.feature.user.mapper.UserModelMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatModelMapper implements Mapper<ChatModel, ChatEntity> {

    @Inject
    UserModelMapper userModelMapper;

    @Inject
    GroupModelMapper groupModelMapper;

    @Inject
    MessageModelMapper messageModelMapper;

    @Inject
    public ChatModelMapper() {
    }

    @Override
    public ChatEntity mapToEntity(ChatModel type) {
        if (type == null) {
            return null;
        }
        return new ChatEntity(userModelMapper.mapToEntity(type.getUser()),
                groupModelMapper.mapToEntity(type.getGroup()),
                messageModelMapper.mapToEntity(type.getLastMessage()));
    }

    @Override
    public ChatModel mapToModel(ChatEntity type) {
        if (type == null) {
            return null;
        }
        return new ChatModel(userModelMapper.mapToModel(type.getUser()),
                groupModelMapper.mapToModel(type.getGroup()),
                messageModelMapper.mapToModel(type.getLastMessage()));
    }
}
