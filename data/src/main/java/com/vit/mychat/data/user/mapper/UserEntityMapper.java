package com.vit.mychat.data.user.mapper;

import com.vit.mychat.data.Mapper;
import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.domain.usecase.user.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserEntityMapper implements Mapper<UserEntity, User> {

    @Inject
    public UserEntityMapper() {
    }

    @Override
    public UserEntity mapToEntity(User type) {
        if (type == null) {
            return null;
        }
        return new UserEntity(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }

    @Override
    public User mapFromEntity(UserEntity type) {
        if (type == null) {
            return null;
        }
        return new User(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(),
                type.getNews(), type.getOnline());
    }
}
