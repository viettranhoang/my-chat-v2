package com.vit.mychat.ui.chat.module;

import android.support.v4.app.Fragment;

import com.vit.mychat.di.scope.PerFragment;
import com.vit.mychat.ui.base.module.BaseFragmentModule;
import com.vit.mychat.ui.chat.ChatFragment;
import com.vit.mychat.ui.chat.listener.OnClickChatItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = {BaseFragmentModule.class})
public abstract class ChatModule {

    @Binds
    @PerFragment
    abstract Fragment ChatFragment(ChatFragment chatFragment);

    @Binds
    @PerFragment
    abstract OnClickChatItemListener onClickChatItemListener(ChatFragment chatFragment);
}
