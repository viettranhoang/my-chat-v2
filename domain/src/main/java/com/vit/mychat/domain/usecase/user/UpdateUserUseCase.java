package com.vit.mychat.domain.usecase.user;

import com.vit.mychat.domain.CompletableUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

@Singleton
public class UpdateUserUseCase extends CompletableUseCase<UpdateUserUseCase.Params> {

    @Inject
    UserRepository userRepository;

    @Inject
    public UpdateUserUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                             @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseSingle(Params params) {
        return userRepository.updateUser(params.user);
    }


    public static final class Params {
        private final User user;

        public Params(User user) {
            this.user = user;
        }

        public static Params forUpdateUser(User user) {
            return new Params(user);
        }
    }
}
