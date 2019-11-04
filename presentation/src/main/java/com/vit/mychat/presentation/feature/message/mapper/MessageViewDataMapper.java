package com.vit.mychat.presentation.feature.message.mapper;


import com.vit.mychat.domain.usecase.message.model.Message;
import com.vit.mychat.presentation.Mapper;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageViewDataMapper implements Mapper<Message, MessageViewData> {

    @Inject
    public MessageViewDataMapper() {
    }

    @Override
    public MessageViewData mapToViewData(Message type) {
        if (type == null) {
            return null;
        }
        return new MessageViewData(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType(), type.getAvatar());
    }

    @Override
    public Message mapFromViewData(MessageViewData type) {
        if (type == null) {
            return null;
        }
        return new Message(type.getMessage(), type.getFrom(), type.isSeen(), type.getTime(), type.getType(), type.getAvatar());
    }
}
