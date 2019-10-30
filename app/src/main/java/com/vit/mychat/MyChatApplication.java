package com.vit.mychat;

import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.vit.mychat.di.DaggerAppComponent;
import com.vit.mychat.util.AppLifecycleObserver;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyChatApplication extends DaggerApplication {

    private AppLifecycleObserver appLifecycleObserver;

    @Override
    protected AndroidInjector<? extends MyChatApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appLifecycleObserver = new AppLifecycleObserver();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
