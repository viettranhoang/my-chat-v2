package com.vit.mychat.data.auth.source;

import io.reactivex.Completable;

public interface AuthRemote {

    Completable login(String email, String password);

    Completable register(String email, String password);

    void signOut();
}
