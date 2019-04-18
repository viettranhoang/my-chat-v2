package com.vit.mychat.domain.usecase.user.repository;

import com.vit.mychat.domain.usecase.user.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserRepository {

    Observable<User> getUserById(String userId);

    Completable updateUser(User user);

    Observable<String> getRelationship(String fromId, String toId);

    Completable updateUserRelationship(String fromId, String toId, String type);

    Observable<List<User>> getUserList();
}
