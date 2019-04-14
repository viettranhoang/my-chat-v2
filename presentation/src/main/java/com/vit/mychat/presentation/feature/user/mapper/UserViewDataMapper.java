package com.vit.mychat.presentation.feature.user.mapper;


import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.presentation.Mapper;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserViewDataMapper implements Mapper<User, UserViewData> {

    @Inject
    public UserViewDataMapper() {
    }


    @Override
    public UserViewData mapToViewData(User type) {
        if(type == null) {
            return null;
        }
        return new UserViewData(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(), type.getNews(), type.getOnline());
    }

    @Override
    public User mapFromViewData(UserViewData type) {
        if(type == null) {
            return null;
        }
        return new User(type.getId(), type.getName(), type.getStatus(), type.getAvatar(), type.getCover(), type.getNews(), type.getOnline());
    }
}
