package com.vit.mychat.ui;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.util.GlideApp;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initBottomNavigationView();
        LoginActivity.moveLoginActivity(this);

    }

    private void initBottomNavigationView() {

        mViewBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    fragment = new ChatFragment();
                    loadFragment(fragment);

                    mTextTitle.setText(getString(R.string.chat));
                    mImageContacts.setVisibility(View.INVISIBLE);
                    mImageAddFriends.setVisibility(View.INVISIBLE);
                    mImageCamera.setVisibility(View.VISIBLE);
                    mImageCreate.setVisibility(View.VISIBLE);
                    return true;
                case R.id.menu_friends:
                    fragment = new FriendsFragment();
                    loadFragment(fragment);

                    mTextTitle.setText(getString(R.string.friends));
                    mImageContacts.setVisibility(View.VISIBLE);
                    mImageAddFriends.setVisibility(View.VISIBLE);
                    mImageCamera.setVisibility(View.INVISIBLE);
                    mImageCreate.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.menu_bot:
                    fragment = new BotFragment();
                    loadFragment(fragment);

                    mTextTitle.setText(getString(R.string.bot));
                    mImageContacts.setVisibility(View.INVISIBLE);
                    mImageAddFriends.setVisibility(View.INVISIBLE);
                    mImageCamera.setVisibility(View.INVISIBLE);
                    mImageCreate.setVisibility(View.INVISIBLE);

                    return true;
            }
            return false;
        });
        mViewBottomNavigation.setSelectedItemId(R.id.menu_chat);
    }

    @OnClick(R.id.image_avatar)
    void onClickAvatar() {
        Toast.makeText(this, "avatar", Toast.LENGTH_SHORT).show();

        ProfileActivity.moveProfileActivity(this);
    }

    @OnClick(R.id.image_create)
    void onClickCreate() {
        Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.image_camera)
    void onClickCamera() {
        Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
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

        GlideApp.with(this)
                .load("http://gnemart.com/wp-content/uploads/2018/10/gau-bong-quobee-1.jpg")
                .circleCrop()
                .into(mImageAvatar);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
