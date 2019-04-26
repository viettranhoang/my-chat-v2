package com.vit.mychat.data.message.mapper;

import com.vit.mychat.data.Mapper;
import com.vit.mychat.data.message.model.MessageEntity;
import com.vit.mychat.domain.usecase.message.model.Message;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageEntityMapper implements Mapper<MessageEntity, Message> {

    @Inject
    public MessageEntityMapper() {
    }

    @Override
    public MessageEntity mapToEntity(Message type) {
        if (type == null) {
            return null;
        }
        return new MessageEntity(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType());
    }

    @Override
    public Message mapFromEntity(MessageEntity type) {
        if (type == null) {
            return null;
        }
        return new Message(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType());
    }
}
