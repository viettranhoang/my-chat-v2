package com.vit.mychat.di;

import android.app.Application;

import com.vit.mychat.MyChatApplication;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.MainActivityModule;
import com.vit.mychat.ui.login.LoginActivity;
import com.vit.mychat.ui.login.LoginModule;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.message.MessageModule;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.profile.ProfileModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class AppModule {

    @Singleton
    @Binds
    abstract Application provideContext(MyChatApplication application);

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity profileActivityInjector();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivityInjector();

    @ContributesAndroidInjector(modules = MessageModule.class)
    abstract MessageActivity messageActivityInjector();

}
