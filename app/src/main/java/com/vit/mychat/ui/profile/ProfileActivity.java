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
                .load("http://gnemart.com/wp-content/uploads/2018/10/gau-bong-quobee-1.jpg")
                .circleCrop()
                .into(mImageAvatar);

        GlideApp.with(this)
                .load("https://www.incimages.com/uploaded_files/image/970x450/getty_509107562_2000133320009280346_351827.jpg")
                .centerCrop()
                .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(mImageBackground);

    }
}
