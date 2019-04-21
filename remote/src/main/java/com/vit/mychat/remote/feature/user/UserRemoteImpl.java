package com.vit.mychat.remote.feature.user;

import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.user.mapper.UserModelMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class UserRemoteImpl implements UserRemote {

    @Inject
    UserModelMapper mapper;

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public UserRemoteImpl() {
    }

    @Override
    public Observable<UserEntity> getUserById(String userId) {
        return myChatFirestore.getUserById(userId)
                .map(userModel -> mapper.mapToEntity(userModel));
    }

    @Override
    public Completable updateUser(UserEntity userEntity) {
        return myChatFirestore.updateUser(mapper.mapToModel(userEntity));
    }

    @Override
    public Observable<String> getRelationship(String fromId, String toId) {
        return myChatFirestore.getRelationship(fromId, toId);
    }

    @Override
    public Completable updateUserRelationship(String fromId, String toId, String type) {
        return myChatFirestore.updateUserRelationship(fromId, toId, type);
    }

    @Override
    public Observable<List<UserEntity>> getUserList() {
        return myChatFirestore.getUserList()
                .flatMap(userModels -> Observable.fromIterable(userModels)
                        .map(userModel -> mapper.mapToEntity(userModel))
                        .toList()
                        .toObservable());
    }

    @Override
    public Single<List<UserEntity>> getFriendList(String userId, String type) {
        return myChatFirestore.getIdFriendList(userId, type)
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(myChatFirestore::getUserByIdSingle)
                .map(mapper::mapToEntity)
                .toList();
    }
}
