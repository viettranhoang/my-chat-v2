package com.vit.mychat.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.vit.mychat.di.key.ViewModelKey;
import com.vit.mychat.presentation.feature.MyChatViewModelFactory;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MyChatViewModelFactory myChatViewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(GetUserByIdViewModel.class)
    abstract ViewModel bindGetUserByIdViewModel(GetUserByIdViewModel getUserByIdViewModel);

}
