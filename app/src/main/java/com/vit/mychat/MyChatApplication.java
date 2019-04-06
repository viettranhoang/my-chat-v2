package com.vit.mychat;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.vit.mychat.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyChatApplication extends DaggerApplication {

    public static final String TAG = MyChatApplication.class.getSimpleName();

    @Override
    protected AndroidInjector<? extends MyChatApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
