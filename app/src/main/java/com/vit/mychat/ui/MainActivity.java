package com.vit.mychat.ui;

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
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.bot.BotFragment;
import com.vit.mychat.ui.chat.ChatFragment;
import com.vit.mychat.ui.friends.FriendsFragment;
import com.vit.mychat.ui.login.LoginActivity;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.util.GlideApp;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initBottomNavigationView();

    }

    @OnClick(R.id.image_avatar)
    void onClickAvatar() {
        Toast.makeText(this, "avatar", Toast.LENGTH_SHORT).show();
        ProfileActivity.moveProfileActivity(this);
    }

    @OnClick(R.id.image_create)
    void onClickCreate() {
        Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
        LoginActivity.moveLoginActivity(this);
    }

    @OnClick(R.id.image_camera)
    void onClickCamera() {
        Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
        MessageActivity.moveMessageActivity(this);
    }

    @OnClick(R.id.image_add_friends)
    void onClickAddFriends() {
        Toast.makeText(this, "add friends", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.image_contacts)
    void onClickContacts() {
        Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.main_toolbar));
        GlideApp.with(this)
                .load("https://i.ytimg.com/vi/o1bL0Qe_yoU/hqdefault.jpg")
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
