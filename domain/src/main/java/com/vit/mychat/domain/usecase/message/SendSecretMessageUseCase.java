package com.vit.mychat.domain.usecase.message;

import com.vit.mychat.domain.CompletableUseCase;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

@Singleton
public class SendSecretMessageUseCase extends CompletableUseCase<SendSecretMessageUseCase.Params> {

    @Inject
    MessageRepository messageRepository;

    @Inject
    public SendSecretMessageUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                    @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseSingle(Params params) {
        return messageRepository.sendSecretMessage(params.userId, params.message, params.type);
    }

    public static final class Params {
        private final String userId;

        private final String message;

        private final String type;

        public Params(String userId, String message, String type) {
            this.userId = userId;
            this.message = message;
            this.type = type;
        }

        public static SendSecretMessageUseCase.Params forSendMessage(String userId, String message, String type) {
            return new SendSecretMessageUseCase.Params(userId, message, type);
        }
    }
}
