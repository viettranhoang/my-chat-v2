package com.vit.mychat.remote.feature.message;

import com.vit.mychat.data.message.model.MessageEntity;
import com.vit.mychat.data.message.source.MessageRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.message.mapper.MessageModelMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class MessageRemoteImpl implements MessageRemote {

    @Inject
    MessageModelMapper mapper;

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public MessageRemoteImpl() {
    }

    @Override
    public Observable<List<MessageEntity>> getMessageList(String userId) {
        return myChatFirestore.getMessageList(userId)
                .flatMap(messageModels -> Observable.fromIterable(messageModels)
                        .map(messageModel -> mapper.mapToEntity(messageModel))
                        .toList()
                        .toObservable());
    }

    @Override
    public Completable sendMessage(String userId, String message, String type) {
        return myChatFirestore.sendMessage(userId, message, type);
    }

    @Override
    public Completable sendSecretMessage(String userId, String message, String type) {
        return myChatFirestore.sendSecretMessage(userId, message, type);
    }
}
