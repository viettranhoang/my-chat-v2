package com.vit.mychat.remote.feature.user;

import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.remote.feature.user.mapper.UserModelMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class UserRemoteImpl implements UserRemote {

    @Inject
    UserModelMapper mapper;

    @Inject
    public UserRemoteImpl() {
    }

    @Override
    public Single<UserEntity> getUserById(String userId) {
        return null;
    }
}
