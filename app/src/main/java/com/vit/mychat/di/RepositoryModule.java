package com.vit.mychat.di;


import com.vit.mychat.data.user.UserRepositoryImpl;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;
import com.vit.mychat.remote.feature.user.UserRemoteImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract UserRepository userRepository(UserRepositoryImpl userRepository);

    @Singleton
    @Binds
    abstract UserRemote userRemote(UserRemoteImpl userRemote);


}
