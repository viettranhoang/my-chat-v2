package com.vit.mychat.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.util.GlideApp;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.image_messenger)
    ImageView mImageMessenger;

    public static void moveLoginActivity(Activity activity){
        activity.startActivity(new Intent(activity , LoginActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        GlideApp.with(this)
                .load(R.drawable.ic_messenger)
                .into(mImageMessenger);
    }
}