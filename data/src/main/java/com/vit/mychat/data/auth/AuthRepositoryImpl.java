package com.vit.mychat.data.auth;

import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    @Inject
    AuthRemote authRemote;

    @Inject
    public AuthRepositoryImpl() {
    }


    @Override
    public Completable login(String emai, String password) {
        return authRemote.login(emai, password);
    }

    @Override
    public Completable register(String emai, String password) {
        return authRemote.register(emai, password);
    }

    @Override
    public void signOut() {
        authRemote.signOut();
    }
}
