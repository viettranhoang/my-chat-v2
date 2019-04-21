package com.vit.mychat.data.user;

import com.vit.mychat.data.user.mapper.UserEntityMapper;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

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

    @Override
    public Observable<String> getRelationship(String fromId, String toId) {
        return userRemote.getRelationship(fromId, toId);
    }

    @Override
    public Completable updateUserRelationship(String fromId, String toId, String type) {
        return userRemote.updateUserRelationship(fromId, toId, type);
    }

    @Override
    public Observable<List<User>> getUserList() {
        return userRemote.getUserList()
                .flatMap(userEntities -> Observable.fromIterable(userEntities)
                        .map(userEntity -> mapper.mapFromEntity(userEntity))
                        .toList()
                        .toObservable());
    }

    @Override
    public Single<List<User>> getFriendList(String userId, String type) {
        return userRemote.getFriendList(userId, type)
                .flatMap(userEntities -> Observable.fromIterable(userEntities)
                        .map(userEntity -> mapper.mapFromEntity(userEntity))
                        .toList());
    }
}
