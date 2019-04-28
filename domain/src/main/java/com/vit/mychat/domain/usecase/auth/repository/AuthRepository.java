package com.vit.mychat.domain.usecase.auth.repository;

import com.vit.mychat.domain.usecase.user.model.User;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface AuthRepository {

    Completable login(String emai, String password);

    Completable register(String emai, String password);

    void signOut();

    String getCurrentUserId();

    Observable<User> getCurentUser();
}
