package com.vit.mychat.ui.secret.pick_friend.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.secret.pick_friend.SecretPickFriendActivity;
import com.vit.mychat.ui.secret.pick_friend.listener.OnClickSecretItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class SecretPickFriendModule {

    @Binds
    @PerActivity
    abstract OnClickSecretItemListener onClickSearchItemListener(SecretPickFriendActivity secretPickFriendActivity);
}
