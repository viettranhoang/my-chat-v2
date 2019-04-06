package com.vit.mychat.di;

import android.app.Application;

import com.vit.mychat.MyChatApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Module(includes = {ActivityModule.class, RepositoryModule.class, ViewModelModule.class})
public abstract class AppModule {

    @Singleton
    @Binds
    abstract Application application(MyChatApplication application);

    @Singleton
    @Provides
    @Named("SchedulerType.IO")
    static Scheduler schedulerIO() {
        return Schedulers.io();
    }

    @Singleton
    @Provides
    @Named("SchedulerType.COMPUTATION")
    static Scheduler schedulerComputation() {
        return Schedulers.computation();
    }

    @Singleton
    @Provides
    @Named("SchedulerType.UI")
    static Scheduler schedulerUI() {
        return AndroidSchedulers.mainThread();
    }


}
