package com.vit.mychat.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.auth.AuthViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.auth.AuthActivity;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.bot.BotFragment;
import com.vit.mychat.ui.chat.ChatFragment;
import com.vit.mychat.ui.choose.ChooseActivity;
import com.vit.mychat.ui.friends.FriendsFragment;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.request_receive.RequestReceiveActivity;
import com.vit.mychat.ui.request_sent.RequestSentActivity;
import com.vit.mychat.ui.search.SearchActivity;
import com.vit.mychat.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static void moveMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.view_scroll_main)
    NestedScrollView mViewScrollMain;

    @BindView(R.id.view_bottom_navigation)
    BottomNavigationView mViewBottomNavigation;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_camera)
    ImageView mImageCamera;

    @BindView(R.id.image_create)
    ImageView mImageCreate;

    @BindView(R.id.image_contacts)
    ImageView mImageContacts;

    @BindView(R.id.image_add_friends)
    ImageView mImageAddFriends;

    @BindView(R.id.text_title)
    TextView mTextTitle;

    private FragmentManager fragmentManager;
    private Handler fragmentHandler;

    private AuthViewModel authViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel.class);

        if (authViewModel.getCurrentUserId() == null) {
            AuthActivity.moveAuthActivity(this);
            finish();
        }
        Constants.CURRENT_UID = authViewModel.getCurrentUserId();
        authViewModel.getCurrentUser().observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    Constants.CURRENT_USER = (UserViewData) resource.getData();
                    break;
            }
        });

        initToolbar();
        initBottomNavigationView();
    }

    @OnClick(R.id.image_avatar)
    void onClickAvatar() {
        ProfileActivity.moveProfileActivity(this, authViewModel.getCurrentUserId());
    }

    @OnClick(R.id.image_create)
    void onClickCreate() {
        ChooseActivity.moveChooseActivity(this);
    }

    @OnClick(R.id.image_camera)
    void onClickCamera() {
        Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.image_add_friends)
    void onClickAddFriends() {
        RequestSentActivity.moveRequestSentActivity(this);
    }

    @OnClick(R.id.image_contacts)
    void onClickContacts() {
        RequestReceiveActivity.moveRequestReceiveActivity(this);
    }

    @OnClick(R.id.text_search)
    void onClickSearch() {
        SearchActivity.moveSearchActivity(this);
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.main_toolbar));

        GlideApp.with(this)
                .load(Constants.CURRENT_USER.getAvatar())
                .circleCrop()
                .into(mImageAvatar);

    }

    private void initBottomNavigationView() {
        fragmentManager = getSupportFragmentManager();
        fragmentHandler = new Handler();

        mViewBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    switchFragment(ChatFragment.newInstance(), ChatFragment.TAG, false, false);
                    setUpScreen(R.string.chat, View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
                    return true;

                case R.id.menu_friends:
                    switchFragment(FriendsFragment.newInstance(), FriendsFragment.TAG, false, false);
                    setUpScreen(R.string.friends, View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                    return true;

                case R.id.menu_bot:
                    switchFragment(BotFragment.newInstance(), BotFragment.TAG, false, false);
                    setUpScreen(R.string.bot, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                    return true;
            }
            return false;
        });
        mViewBottomNavigation.setSelectedItemId(R.id.menu_chat);

    }

    private void setUpScreen(int title, int contact, int addFriends, int camera, int create) {
        mTextTitle.setText(getString(title));
        mImageContacts.setVisibility(contact);
        mImageAddFriends.setVisibility(addFriends);
        mImageCamera.setVisibility(camera);
        mImageCreate.setVisibility(create);
        mViewScrollMain.post(() -> mViewScrollMain.scrollTo(0, 0));
    }

    public void switchFragment(Fragment fragment, String tag, boolean addToBackStack, boolean clearBackStack) {
        fragmentHandler.post(new RunFragment(fragment, tag, addToBackStack, clearBackStack));
    }

    private class RunFragment implements Runnable {
        Fragment bf;
        String tag;
        boolean addToBackStack;
        boolean clearBackStack;

        RunFragment(Fragment bf, String tag, boolean addToBackStack, boolean clearBackStack) {
            this.bf = bf;
            this.tag = tag;
            this.addToBackStack = addToBackStack;
            this.clearBackStack = clearBackStack;
        }

        public void run() {
            if (clearBackStack) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.layout_fragment, bf, tag);
            if (addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

}
