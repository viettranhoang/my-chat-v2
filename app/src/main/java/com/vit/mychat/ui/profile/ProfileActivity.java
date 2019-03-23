package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.util.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {

    public static void moveProfileActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_background)
    ImageView mImageBackground;

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        GlideApp.with(this)
                .load("https://i.ytimg.com/vi/o1bL0Qe_yoU/hqdefault.jpg")
                .circleCrop()
                .into(mImageAvatar);

        GlideApp.with(this)
                .load("https://2.bp.blogspot.com/-2mOg5hFPs3I/VF1jNAxAUGI/AAAAAAAAE_8/1SFaUKMLU0U/s1600/hinh-anh-sakura-dep-nhat-1.jpg")
                .centerCrop()
                .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(mImageBackground);

    }
}
