package com.vit.mychat.data.message;

import com.vit.mychat.data.message.mapper.MessageEntityMapper;
import com.vit.mychat.data.message.source.MessageRemote;
import com.vit.mychat.domain.usecase.message.model.Message;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class MessageRepositoryImpl implements MessageRepository {

    @Inject
    MessageRemote messageRemote;

    @Inject
    MessageEntityMapper mapper;

    @Inject
    public MessageRepositoryImpl() {
    }


    @Override
    public Observable<List<Message>> getMessageList(String userId) {
        return messageRemote.getMessageList(userId)
                .flatMap(messageEntities -> Observable.fromIterable(messageEntities)
                        .map(messageEntity -> mapper.mapFromEntity(messageEntity))
                        .toList()
                        .toObservable());
    }

    @Override
    public Completable sendMessage(String userId, String message) {
        return messageRemote.sendMessage(userId, message);
    }
}
