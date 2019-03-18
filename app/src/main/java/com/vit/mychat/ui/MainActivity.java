package com.vit.mychat.ui;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.bot.BotFragment;
import com.vit.mychat.ui.chat.ChatFragment;
import com.vit.mychat.ui.friends.FriendsFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_bottom_navigation)
    BottomNavigationView mViewBottomNavigation;


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {

        mViewBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    fragment = new ChatFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_friends:
                    fragment = new FriendsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_bot:
                    fragment = new BotFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        });
        mViewBottomNavigation.setSelectedItemId(R.id.menu_chat);
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
