package com.vit.mychat.ui.choose.module;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.base.module.BaseActivityModule;
import com.vit.mychat.ui.choose.ChooseActivity;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class ChooseModule {

    @Binds
    @PerActivity
    abstract OnClickChooseVerticalItemListener onClickChooseVerticalItemListener(ChooseActivity chooseActivity);

    @Binds
    @PerActivity
    abstract OnClickChooseHorizontalItemListener onClickChooseHorizontalItemListener(ChooseActivity chooseActivity);
}
