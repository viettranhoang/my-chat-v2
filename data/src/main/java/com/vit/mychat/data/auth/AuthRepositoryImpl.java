package com.vit.mychat.data.auth;

import com.vit.mychat.data.auth.source.AuthCache;
import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    @Inject
    AuthCache authCache;

    @Inject
    AuthRemote authRemote;

    @Inject
    public AuthRepositoryImpl() {
    }

    @Override
    public Completable login(String emai, String password) {
        return authRemote.login(emai, password)
                .flatMapCompletable(currentId -> {
                    authCache.saveCurrentUserId(currentId);
                    return Completable.complete();
                });
    }

    @Override
    public Completable register(String emai, String password) {
        return authRemote.register(emai, password)
                .flatMapCompletable(currentId -> {
                    authCache.saveCurrentUserId(currentId);
                    return Completable.complete();
                });
    }

    @Override
    public void signOut() {
        authRemote.signOut();
        authCache.saveCurrentUserId(null);
    }

    @Override
    public String getCurrentUserId() {
        return authCache.getCurrentUserId();
    }
}
