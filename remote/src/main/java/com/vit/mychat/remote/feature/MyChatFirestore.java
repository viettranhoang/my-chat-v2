package com.vit.mychat.remote.feature;

import com.vit.mychat.remote.feature.user.model.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MyChatFirestore {

    /**
     * user
     */
    Single<UserModel> getUserById(String userId);

    Completable updateUser(UserModel userModel);


    /**
     * auth
     */
    Single<String> login(String email, String password);

    Single<String> register(String email, String password);

    void signOut();
}
