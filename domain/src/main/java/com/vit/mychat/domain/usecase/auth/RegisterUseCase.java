package com.vit.mychat.domain.usecase.auth;

import com.vit.mychat.domain.CompletableUseCase;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

@Singleton
public class RegisterUseCase extends CompletableUseCase<RegisterUseCase.Params> {

    @Inject
    AuthRepository authRepository;

    @Inject
    public RegisterUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                        @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseSingle(Params params) {
        return authRepository.register(params.email, params.password);
    }

    public static final class Params {
        private final String email;
        private final String password;

        public Params(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static Params forRegister(String email, String password) {
            return new Params(email, password);
        }
    }
}
