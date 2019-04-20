package com.vit.mychat.remote.feature.user.mapper;


import android.util.Log;

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
        Log.i("UserModelMapper", "mapToEntity: " + type.getName());
        return new UserEntity(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }

    public UserEntity mapToString(String type) {
        if (type == null) {
            return null;
        }
        return new UserEntity(type, "", "", "", "", "", 0);
    }

    @Override
    public UserModel mapToModel(UserEntity type) {
        if (type == null) {
            return null;
        }
        return new UserModel(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }
}
