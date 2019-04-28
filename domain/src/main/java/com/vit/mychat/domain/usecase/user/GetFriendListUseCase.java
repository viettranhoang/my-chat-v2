package com.vit.mychat.domain.usecase.user;

import com.vit.mychat.domain.ObservableUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class GetFriendListUseCase extends ObservableUseCase<List<User>, GetFriendListUseCase.Params> {

    @Inject
    UserRepository userRepository;

    @Inject
    public GetFriendListUseCase(@Named("SchedulerType.IO") Scheduler threadExecutor,
                                @Named("SchedulerType.UI") Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<List<User>> buildUseCaseSingle(Params params) {
        return userRepository.getFriendList(params.userId, params.type);
    }

    public final static class Params {
        private final String userId;
        private final String type;

        public Params(String userId, String type) {
            this.userId = userId;
            this.type = type;
        }

        public static GetFriendListUseCase.Params forGetFriendList(String userId, String type) {
            return new GetFriendListUseCase.Params(userId, type);
        }
    }

}
