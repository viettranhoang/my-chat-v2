package com.vit.mychat.remote.feature;

import com.vit.mychat.remote.feature.user.model.UserModel;

import io.reactivex.Single;

public interface MyChatFirestore {

    Single<UserModel> getUserById(String userId);
}
