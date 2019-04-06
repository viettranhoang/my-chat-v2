package com.vit.mychat.di;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.MainActivityModule;
import com.vit.mychat.ui.login.LoginActivity;
import com.vit.mychat.ui.login.LoginModule;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.message.MessageModule;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.profile.ProfileModule;
import com.vit.mychat.ui.register.RegisterActivity;
import com.vit.mychat.ui.register.RegisterModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity profileActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = MessageModule.class)
    abstract MessageActivity messageActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RegisterModule.class)
    abstract RegisterActivity registerActivityInjector();
}
