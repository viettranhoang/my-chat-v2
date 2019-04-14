package com.vit.mychat.ui.friends.module;

import android.support.v4.app.Fragment;

import com.vit.mychat.di.scope.PerFragment;
import com.vit.mychat.ui.base.module.BaseFragmentModule;
import com.vit.mychat.ui.friends.FriendsFragment;

import dagger.Binds;
import dagger.Module;

@Module(includes = {BaseFragmentModule.class})
public abstract class FriendsModule {

    @Binds
    @PerFragment
    abstract Fragment friendsFragment(FriendsFragment friendsFragment);
}
