package com.vit.mychat.data.user;

import com.vit.mychat.data.user.mapper.UserEntityMapper;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    @Inject
    UserRemote userRemote;

    @Inject
    UserEntityMapper mapper;

    @Inject
    public UserRepositoryImpl() {
    }

    @Override
    public Observable<User> getUserById(String userId) {
        return userRemote.getUserById(userId)
                .map(userEntity -> mapper.mapFromEntity(userEntity));
    }

    @Override
    public Completable updateUser(User user) {
        return userRemote.updateUser(mapper.mapToEntity(user));
    }
}
