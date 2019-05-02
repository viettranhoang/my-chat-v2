package com.vit.mychat.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.vit.mychat.di.key.ViewModelKey;
import com.vit.mychat.presentation.feature.MyChatViewModelFactory;
import com.vit.mychat.presentation.feature.auth.AuthViewModel;
import com.vit.mychat.presentation.feature.chat.GetChatListViewModel;
import com.vit.mychat.presentation.feature.group.CreateGroupViewModel;
import com.vit.mychat.presentation.feature.image.UploadImageViewModel;
import com.vit.mychat.presentation.feature.message.GetMessageListViewModel;
import com.vit.mychat.presentation.feature.message.SendMessageViewModel;
import com.vit.mychat.presentation.feature.news.GetNewsListViewModel;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;
import com.vit.mychat.presentation.feature.user.GetUserListViewModel;
import com.vit.mychat.presentation.feature.user.GetUserRelationshipViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserRelationshipViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MyChatViewModelFactory myChatViewModelFactory);


    /**
     * User
     */
    @Binds
    @IntoMap
    @ViewModelKey(GetUserListViewModel.class)
    abstract ViewModel bindGetUserListViewModel(GetUserListViewModel getUserListViewModel);

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
    @ViewModelKey(UpdateUserRelationshipViewModel.class)
    abstract ViewModel bindUpdateUserRelationshipViewModel(UpdateUserRelationshipViewModel updateUserRelationshipViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GetFriendListViewModel.class)
    abstract ViewModel bindGetFriendListViewModel(GetFriendListViewModel getFriendListViewModel);

    /**
     * Auth
     */
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);


    /**
     * message
     */
    @Binds
    @IntoMap
    @ViewModelKey(GetMessageListViewModel.class)
    abstract ViewModel bindGetMessageListViewModel(GetMessageListViewModel getMessageListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SendMessageViewModel.class)
    abstract ViewModel bindSendMessageViewModel(SendMessageViewModel sendMessageViewModel);

    /**
     * chat
     */
    @Binds
    @IntoMap
    @ViewModelKey(GetChatListViewModel.class)
    abstract ViewModel bindGetChatListViewModel(GetChatListViewModel getChatListViewModel);

    /**
     * news
     */
    @Binds
    @IntoMap
    @ViewModelKey(GetNewsListViewModel.class)
    abstract ViewModel bindGetNewsListViewModel(GetNewsListViewModel getNewsListViewModel);

    /**
     * image
     */
    @Binds
    @IntoMap
    @ViewModelKey(UploadImageViewModel.class)
    abstract ViewModel bindUploadImageViewModel(UploadImageViewModel uploadImageViewModel);

    /**
     * group
     */
    @Binds
    @IntoMap
    @ViewModelKey(CreateGroupViewModel.class)
    abstract ViewModel bindCreateGroupViewModel(CreateGroupViewModel createGroupViewModel);
}
