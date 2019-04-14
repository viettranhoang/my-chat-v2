package com.vit.mychat.domain.usecase.auth.repository;

import io.reactivex.Completable;

public interface AuthRepository {

    Completable login(String emai, String password);

    Completable register(String emai, String password);

    void signOut();

}
