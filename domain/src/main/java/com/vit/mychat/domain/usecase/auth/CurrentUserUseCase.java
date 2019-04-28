package com.vit.mychat.domain.usecase.auth;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;
import com.vit.mychat.domain.usecase.user.model.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class CurrentUserUseCase extends ObservableUseCase<User, Void> {

    @Inject
    AuthRepository authRepository;

    @Inject
    public CurrentUserUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                              @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<User> buildUseCaseSingle(Void aVoid) {
        return authRepository.getCurentUser();
    }

    public String getCurrentUserId() {
        return authRepository.getCurrentUserId();
    }

}
