package com.vit.mychat.ui.secret.module;

import android.support.v4.app.Fragment;

import com.vit.mychat.di.scope.PerFragment;
import com.vit.mychat.ui.base.module.BaseFragmentModule;
import com.vit.mychat.ui.secret.SecretFragment;

import dagger.Binds;
import dagger.Module;

@Module(includes = {BaseFragmentModule.class})
public abstract class SecretModule {

    @Binds
    @PerFragment
    abstract Fragment botFragment(SecretFragment secretFragment);


}
