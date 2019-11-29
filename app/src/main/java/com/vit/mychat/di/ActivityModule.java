package com.vit.mychat.di;

import com.vit.mychat.di.scope.PerActivity;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.MainActivityModule;
import com.vit.mychat.ui.auth.AuthActivity;
import com.vit.mychat.ui.auth.AuthModule;
import com.vit.mychat.ui.choose.ChooseActivity;
import com.vit.mychat.ui.choose.module.ChooseModule;
import com.vit.mychat.ui.message_group.MessageGroupActivity;
import com.vit.mychat.ui.message_group.module.MessageGroupModule;
import com.vit.mychat.ui.message_secret.MessageSecretActivity;
import com.vit.mychat.ui.message_secret.module.MessageSecretModule;
import com.vit.mychat.ui.news.NewsActivity;
import com.vit.mychat.ui.news.module.NewsModule;
import com.vit.mychat.ui.request_receive.RequestReceiveActivity;
import com.vit.mychat.ui.request_receive.module.RequestReceiveModule;
import com.vit.mychat.ui.request_sent.RequestSentActivity;
import com.vit.mychat.ui.request_sent.module.RequestSentModule;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.message.module.MessageModule;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.profile.ProfileModule;
import com.vit.mychat.ui.search.SearchActivity;
import com.vit.mychat.ui.search.module.SearchModule;
import com.vit.mychat.ui.secret.pick_friend.SecretPickFriendActivity;
import com.vit.mychat.ui.secret.pick_friend.module.SecretPickFriendModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity profileActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = AuthModule.class)
    abstract AuthActivity loginActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = MessageModule.class)
    abstract MessageActivity messageActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = MessageGroupModule.class)
    abstract MessageGroupActivity messageGroupActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RequestReceiveModule.class)
    abstract RequestReceiveActivity requestReceiveActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RequestSentModule.class)
    abstract RequestSentActivity requestSentActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchActivity searchActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = NewsModule.class)
    abstract NewsActivity newsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ChooseModule.class)
    abstract ChooseActivity chooseActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SecretPickFriendModule.class)
    abstract SecretPickFriendActivity secretPickFriendActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = MessageSecretModule.class)
    abstract MessageSecretActivity messageSecretActivity();

}
