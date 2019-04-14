package com.vit.mychat.di;


import com.vit.mychat.data.auth.AuthRepositoryImpl;
import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.data.user.UserRepositoryImpl;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;
import com.vit.mychat.remote.feature.auth.AuthRemoteImpl;
import com.vit.mychat.remote.feature.user.UserRemoteImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract AuthRepository authRepository(AuthRepositoryImpl authRepository);

    @Singleton
    @Binds
    abstract AuthRemote authRemote(AuthRemoteImpl authRemote);

    @Singleton
    @Binds
    abstract UserRepository userRepository(UserRepositoryImpl userRepository);

    @Singleton
    @Binds
    abstract UserRemote userRemote(UserRemoteImpl userRemote);


}
