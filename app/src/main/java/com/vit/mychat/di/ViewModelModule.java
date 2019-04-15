package com.vit.mychat.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.vit.mychat.di.key.ViewModelKey;
import com.vit.mychat.presentation.feature.MyChatViewModelFactory;
import com.vit.mychat.presentation.feature.auth.AuthViewModel;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;
import com.vit.mychat.presentation.feature.user.GetUserRelationshipViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;

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

    @Binds
    @IntoMap
    @ViewModelKey(UpdateUserViewModel.class)
    abstract ViewModel bindUpdateUserViewModel(UpdateUserViewModel updateUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GetUserRelationshipViewModel.class)
    abstract ViewModel bindGetUserRelationshipViewModel(GetUserRelationshipViewModel getUserRelationshipViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

}
