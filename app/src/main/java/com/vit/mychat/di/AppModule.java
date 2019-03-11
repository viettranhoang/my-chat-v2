package com.vit.mychat.di;

import android.app.Application;

import com.vit.vitapp.VitApplication;
import com.vit.vitapp.ui.contact.ContactActivity;
import com.vit.vitapp.ui.contact.module.ContactActivityModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class AppModule {

    @Singleton
    @Binds
    abstract Application provideContext(VitApplication application);

    @ContributesAndroidInjector(modules = ContactActivityModule.class)
    abstract ContactActivity contactActivityInjector();

}
