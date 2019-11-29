package com.vit.mychat.data.chat.source;

import com.vit.mychat.data.chat.model.ChatEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ChatRemote {

    Observable<List<ChatEntity>> getChatList();

    Observable<List<ChatEntity>> getSecretChatList();
}
