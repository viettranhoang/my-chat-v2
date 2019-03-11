package com.vit.mychat;

import com.vit.mychat.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyChatApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends MyChatApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
