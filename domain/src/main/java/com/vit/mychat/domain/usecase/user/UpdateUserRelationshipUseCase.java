package com.vit.mychat.domain.usecase.user;

import com.vit.mychat.domain.CompletableUseCase;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

@Singleton
public class UpdateUserRelationshipUseCase extends CompletableUseCase<UpdateUserRelationshipUseCase.Params> {

    @Inject
    UserRepository userRepository;

    @Inject
    public UpdateUserRelationshipUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                         @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseSingle(Params params) {
        return userRepository.updateUserRelationship(params.fromId, params.toId, params.type);
    }

    public static final class Params {
        private final String fromId;

        private final String toId;

        private final String type;

        public Params(String fromId, String toId, String type) {
            this.fromId = fromId;
            this.toId = toId;
            this.type = type;
        }

        public static UpdateUserRelationshipUseCase.Params forUpdateRelationship(String fromId, String toId, String type) {
            return new UpdateUserRelationshipUseCase.Params(fromId, toId, type);
        }
    }
}
