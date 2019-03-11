package com.vit.mychat.di;

import android.app.Application;

import com.vit.mychat.MyChatApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class AppModule {

    @Singleton
    @Binds
    abstract Application provideContext(MyChatApplication application);


}
