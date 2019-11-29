package com.vit.mychat.domain.usecase.chat;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.chat.model.Chat;
import com.vit.mychat.domain.usecase.chat.repository.ChatRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class GetSecretChatListUseCase extends ObservableUseCase<List<Chat>, Void> {

    @Inject
    ChatRepository chatRepository;

    @Inject
    public GetSecretChatListUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                    @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<List<Chat>> buildUseCaseSingle(Void aVoid) {
        return chatRepository.getSecretChatList();
    }
}
