package com.vit.mychat.di;


import com.vit.mychat.cache.features.auth.AuthCacheImpl;
import com.vit.mychat.data.auth.AuthRepositoryImpl;
import com.vit.mychat.data.auth.source.AuthCache;
import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.data.message.MessageRepositoryImpl;
import com.vit.mychat.data.message.source.MessageRemote;
import com.vit.mychat.data.user.UserRepositoryImpl;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;
import com.vit.mychat.remote.feature.auth.AuthRemoteImpl;
import com.vit.mychat.remote.feature.message.MessageRemoteImpl;
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
    abstract AuthCache authCache(AuthCacheImpl authCache);

    @Singleton
    @Binds
    abstract UserRepository userRepository(UserRepositoryImpl userRepository);

    @Singleton
    @Binds
    abstract UserRemote userRemote(UserRemoteImpl userRemote);

    @Singleton
    @Binds
    abstract MessageRepository messageRepository(MessageRepositoryImpl messageRepository);

    @Singleton
    @Binds
    abstract MessageRemote messageRemote(MessageRemoteImpl messageRemote);


}
