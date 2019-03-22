package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.content.Intent;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;

public class ProfileActivity extends BaseActivity {

    public static void moveProfileActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        showToast("Hello");
    }
}
