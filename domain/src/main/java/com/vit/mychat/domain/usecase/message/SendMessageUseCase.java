package com.vit.mychat.domain.usecase.message;

import com.vit.mychat.domain.CompletableUseCase;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

@Singleton
public class SendMessageUseCase extends CompletableUseCase<SendMessageUseCase.Params> {

    @Inject
    MessageRepository messageRepository;

    @Inject
    public SendMessageUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                              @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseSingle(Params params) {
        return messageRepository.sendMessage(params.userId, params.message);
    }

    public static final class Params {
        private final String userId;

        private final String message;

        public Params(String userId, String message) {
            this.userId = userId;
            this.message = message;
        }

        public static SendMessageUseCase.Params forSendMessage(String userId, String message) {
            return new SendMessageUseCase.Params(userId, message);
        }
    }
}
