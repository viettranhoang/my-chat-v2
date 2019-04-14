package com.vit.mychat.domain.usecase.user.repository;

import com.vit.mychat.domain.usecase.user.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRepository {

    Single<User> getUserById(String userId);

    Completable updateUser(User user);


}
