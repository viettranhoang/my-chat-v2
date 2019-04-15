package com.vit.mychat.domain.usecase.user;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class GetUserRelationshipUseCase extends ObservableUseCase<String, GetUserRelationshipUseCase.Params> {

    @Inject
    UserRepository userRepository;

    @Inject
    public GetUserRelationshipUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                      @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildUseCaseSingle(Params params) {
        return userRepository.getRelationship(params.fromId, params.toId);
    }

    public final static class Params {
        private final String fromId;

        private final String toId;

        public Params(String fromId, String toId) {
            this.fromId = fromId;
            this.toId = toId;
        }

        public static Params forRelationship(String fromId, String toId) {
            return new Params(fromId, toId);
        }
    }
}