package com.vit.mychat.domain.usecase.chat.repository;

import com.vit.mychat.domain.usecase.chat.model.Chat;

import java.util.List;

import io.reactivex.Observable;

public interface ChatRepository {

    Observable<List<Chat>> getChatList();
}
