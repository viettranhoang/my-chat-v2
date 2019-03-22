package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.util.GlideApp;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_background)
    ImageView mImageBackground;

    public static void moveProfileActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        GlideApp.with(this)
                .load(R.drawable.kim_avatar)
                .circleCrop()
                .into(mImageAvatar);

        GlideApp.with(this)
                .load(R.drawable.background_avatar)
//                .centerCrop()
                .into(mImageBackground);

        showToast("Hello");
    }
}
