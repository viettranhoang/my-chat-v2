package com.vit.mychat.di;


import com.vit.mychat.MyChatApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<MyChatApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyChatApplication> {
    }
}
