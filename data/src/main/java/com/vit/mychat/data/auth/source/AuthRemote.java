package com.vit.mychat.data.auth.source;

import io.reactivex.Single;

public interface AuthRemote {

    Single<String> login(String email, String password);

    Single<String> register(String email, String password);

    void signOut();
}
