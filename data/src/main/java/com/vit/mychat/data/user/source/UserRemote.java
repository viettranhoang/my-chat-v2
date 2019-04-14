package com.vit.mychat.data.user.source;

import com.vit.mychat.data.user.model.UserEntity;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserRemote {

    Observable<UserEntity> getUserById(String userId);

    Completable updateUser(UserEntity userEntity);


}
