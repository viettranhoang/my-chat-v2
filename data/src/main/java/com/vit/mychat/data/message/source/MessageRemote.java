package com.vit.mychat.data.message.source;

import com.vit.mychat.data.message.model.MessageEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface MessageRemote {

    Observable<List<MessageEntity>> getMessageList(String userId);

    Completable sendMessage(String userId, String message, String type);
}
