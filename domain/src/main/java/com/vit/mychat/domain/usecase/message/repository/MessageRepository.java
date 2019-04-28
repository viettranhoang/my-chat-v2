package com.vit.mychat.domain.usecase.message.repository;

import com.vit.mychat.domain.usecase.message.model.Message;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface MessageRepository {

    Observable<List<Message>> getMessageList(String userId);

    Completable sendMessage(String userId, String message);


}
