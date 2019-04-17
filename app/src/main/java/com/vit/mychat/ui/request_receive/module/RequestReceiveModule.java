package com.vit.mychat.ui.request_receive.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.request_receive.RequestReceiveActivity;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class RequestReceiveModule {

    @Binds
    @PerActivity
    abstract OnClickRequestReceiveItemListener onClickkRequestReceiveItemListener(RequestReceiveActivity userReceiveRequestActivity);
}
