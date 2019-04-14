package com.vit.mychat.domain.usecase.user;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.SingleUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class GetUserByIdUseCase extends ObservableUseCase<User, GetUserByIdUseCase.Params> {

    @Inject
    UserRepository userRepository;

    @Inject
    public GetUserByIdUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                              @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<User> buildUseCaseSingle(Params params) {
        return userRepository.getUserById(params.id);
    }

    public final static class Params {
        private final String id;

        public Params(String id) {
            this.id = id;
        }

        public static Params forUserById(String id) {
            return new Params(id);
        }
    }
}
