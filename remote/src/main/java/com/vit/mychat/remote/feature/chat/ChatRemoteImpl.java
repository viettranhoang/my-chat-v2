package com.vit.mychat.remote.feature.chat;

import com.vit.mychat.data.chat.model.ChatEntity;
import com.vit.mychat.data.chat.source.ChatRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.chat.mapper.ChatModelMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class ChatRemoteImpl implements ChatRemote {

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    ChatModelMapper mapper;

    @Inject
    public ChatRemoteImpl() {
    }

    @Override
    public Observable<List<ChatEntity>> getChatList() {
        return myChatFirestore.getChatList()
                .flatMap(chatModels -> Observable.fromIterable(chatModels)
                        .map(mapper::mapToEntity)
                        .toSortedList((o1, o2) -> Long.valueOf(o2.getLastMessage().getTime())
                                .compareTo(Long.valueOf(o1.getLastMessage().getTime())))
                        .toObservable());
    }
}
