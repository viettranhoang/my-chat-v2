package com.vit.mychat.domain.usecase.message;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.message.model.Message;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class GetMessageListUseCase extends ObservableUseCase<List<Message>, Void> {

    @Inject
    MessageRepository messageRepository;

    @Inject
    public GetMessageListUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                 @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<List<Message>> buildUseCaseSingle(Void aVoid) {
        return messageRepository.getMessageList();
    }
}
