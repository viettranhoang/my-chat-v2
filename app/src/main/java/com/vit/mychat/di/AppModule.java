package com.vit.mychat.di;

import android.app.Application;

import com.vit.mychat.MyChatApplication;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.MainActivityModule;

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
}
