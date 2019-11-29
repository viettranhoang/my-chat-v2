package com.vit.mychat.di;


import com.vit.mychat.cache.features.auth.AuthCacheImpl;
import com.vit.mychat.cache.features.secret_message.SecretCacheImpl;
import com.vit.mychat.data.auth.AuthRepositoryImpl;
import com.vit.mychat.data.auth.source.AuthCache;
import com.vit.mychat.data.auth.source.AuthRemote;
import com.vit.mychat.data.chat.ChatRepositoryImpl;
import com.vit.mychat.data.chat.source.ChatRemote;
import com.vit.mychat.data.group.GroupRepositoryImpl;
import com.vit.mychat.data.group.source.GroupRemote;
import com.vit.mychat.data.image.ImageRepositoryImpl;
import com.vit.mychat.data.image.source.ImageRemote;
import com.vit.mychat.data.message.MessageRepositoryImpl;
import com.vit.mychat.data.message.source.MessageRemote;
import com.vit.mychat.data.news.NewsRepositoryImpl;
import com.vit.mychat.data.news.source.NewsRemote;
import com.vit.mychat.data.secret_message.SecretRepositoryImpl;
import com.vit.mychat.data.secret_message.source.SecretCache;
import com.vit.mychat.data.secret_message.source.SecretRemote;
import com.vit.mychat.data.user.UserRepositoryImpl;
import com.vit.mychat.data.user.source.UserRemote;
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository;
import com.vit.mychat.domain.usecase.chat.repository.ChatRepository;
import com.vit.mychat.domain.usecase.group.repository.GroupRepository;
import com.vit.mychat.domain.usecase.image.repository.ImageRepository;
import com.vit.mychat.domain.usecase.message.repository.MessageRepository;
import com.vit.mychat.domain.usecase.news.repository.NewsRepository;
import com.vit.mychat.domain.usecase.secret.repository.SecretRepository;
import com.vit.mychat.domain.usecase.user.repository.UserRepository;
import com.vit.mychat.remote.feature.auth.AuthRemoteImpl;
import com.vit.mychat.remote.feature.chat.ChatRemoteImpl;
import com.vit.mychat.remote.feature.group.GroupRemoteImpl;
import com.vit.mychat.remote.feature.image.ImageRemoteImpl;
import com.vit.mychat.remote.feature.message.MessageRemoteImpl;
import com.vit.mychat.remote.feature.news.NewsRemoteImpl;
import com.vit.mychat.remote.feature.secret.SecretRemoteImpl;
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

    @Singleton
    @Binds
    abstract ChatRepository chatRepository(ChatRepositoryImpl chatRepository);

    @Singleton
    @Binds
    abstract ChatRemote chatRemote(ChatRemoteImpl chatRemote);

    @Singleton
    @Binds
    abstract NewsRepository newsRepository(NewsRepositoryImpl newsRepository);

    @Singleton
    @Binds
    abstract NewsRemote newsRemote(NewsRemoteImpl newsRemote);

    @Singleton
    @Binds
    abstract ImageRepository imageRepository(ImageRepositoryImpl imageRepository);

    @Singleton
    @Binds
    abstract ImageRemote imageRemote(ImageRemoteImpl imageRemote);

    @Singleton
    @Binds
    abstract GroupRepository groupRepository(GroupRepositoryImpl groupRepository);

    @Singleton
    @Binds
    abstract GroupRemote groupRemote(GroupRemoteImpl groupRemote);

    @Singleton
    @Binds
    abstract SecretCache secretMessageCache(SecretCacheImpl secretMessageCache);

    @Singleton
    @Binds
    abstract SecretRemote secretRemote(SecretRemoteImpl secretRemote);

    @Singleton
    @Binds
    abstract SecretRepository SecretRepository(SecretRepositoryImpl secretRepository);
}
