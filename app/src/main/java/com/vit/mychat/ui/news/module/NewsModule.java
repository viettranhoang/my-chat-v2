package com.vit.mychat.ui.news.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.news.NewsActivity;
import com.vit.mychat.ui.news.listener.OnClickNewsItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class NewsModule {

    @Binds
    @PerActivity
    abstract OnClickNewsItemListener onClickNewsItemListener(NewsActivity newsActivity);
}
