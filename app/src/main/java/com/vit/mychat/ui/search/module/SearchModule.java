package com.vit.mychat.ui.search.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.search.SearchActivity;
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class SearchModule {

    @Binds
    @PerActivity
    abstract OnClickSearchItemListener onClickSearchItemListener(SearchActivity searchActivity);
}
