package com.vit.mychat.remote.feature.auth;

import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class AuthRemoteImpl implements AuthRemote {

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public AuthRemoteImpl() {
    }

    @Override
    public Completable login(String email, String password) {
        return myChatFirestore.login(email, password);
    }

    @Override
    public Completable register(String email, String password) {
        return myChatFirestore.register(email, password);
    }

    @Override
    public void signOut() {
        myChatFirestore.signOut();
    }
}
