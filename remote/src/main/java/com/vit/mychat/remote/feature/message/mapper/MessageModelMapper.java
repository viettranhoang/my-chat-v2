package com.vit.mychat.remote.feature.message.mapper;


import com.vit.mychat.data.message.model.MessageEntity;
import com.vit.mychat.remote.common.Mapper;
import com.vit.mychat.remote.feature.message.model.MessageModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageModelMapper implements Mapper<MessageModel, MessageEntity> {

    @Inject
    public MessageModelMapper() {
    }

    @Override
    public MessageEntity mapToEntity(MessageModel type) {
        if (type == null) {
            return null;
        }
        return new MessageEntity(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType());
    }

    @Override
    public MessageModel mapToModel(MessageEntity type) {
        if (type == null) {
            return null;
        }
        return new MessageModel(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType());
    }
}
