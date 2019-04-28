package com.vit.mychat.data.chat;

import com.vit.mychat.data.chat.mapper.ChatEntityMapper;
import com.vit.mychat.data.chat.source.ChatRemote;
import com.vit.mychat.domain.usecase.chat.model.Chat;
import com.vit.mychat.domain.usecase.chat.repository.ChatRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class ChatRepositoryImpl implements ChatRepository {

    @Inject
    ChatRemote chatRemote;

    @Inject
    ChatEntityMapper mapper;

    @Inject
    public ChatRepositoryImpl() {
    }

    @Override
    public Observable<List<Chat>> getChatList() {
        return chatRemote.getChatList()
                .flatMap(chatEntities -> Observable.fromIterable(chatEntities)
                        .map(mapper::mapFromEntity)
                        .toList()
                        .toObservable());
    }
}
