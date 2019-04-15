package com.vit.mychat.remote.feature.user;

import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.user.mapper.UserModelMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

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


}
