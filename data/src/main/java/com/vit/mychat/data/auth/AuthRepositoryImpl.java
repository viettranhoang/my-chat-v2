package com.vit.mychat.data.auth;

import com.vit.mychat.data.auth.source.AuthCache;
import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.data.user.mapper.UserEntityMapper;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;
import com.vit.mychat.domain.usecase.user.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    @Inject
    AuthCache authCache;

    @Inject
    AuthRemote authRemote;

    @Inject
    UserRemote userRemote;

    @Inject
    UserEntityMapper mapper;

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
        authCache.signOut();
    }

    @Override
    public String getCurrentUserId() {
        return authCache.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String uid) {
        authCache.setCurrentUserId(uid);
    }

    @Override
    public Observable<User> getCurentUser() {
        return userRemote.getUserById(getCurrentUserId())
                .map(userEntity -> mapper.mapFromEntity(userEntity));
    }
}
