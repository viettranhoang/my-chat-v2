package com.vit.mychat.ui.request_sent.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.request_sent.RequestSentActivity;
import com.vit.mychat.ui.request_sent.listener.OnClickRequestSentItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class RequestSentModule {

    @Binds
    @PerActivity
    abstract OnClickRequestSentItemListener onClickRequestSentItemListener(RequestSentActivity requestSentActivity);
}
