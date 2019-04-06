package com.vit.mychat.remote.feature.user.mapper;


import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.remote.common.Mapper;
import com.vit.mychat.remote.feature.user.model.UserModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserModelMapper implements Mapper<UserModel, UserEntity> {

    @Inject
    public UserModelMapper() {
    }

    @Override
    public UserEntity mapToEntity(UserModel type) {
        if (type == null) {
            return null;
        }
        return new UserEntity(type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }

    @Override
    public UserModel mapToModel(UserEntity type) {
        if (type == null) {
            return null;
        }
        return new UserModel(type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }
}
