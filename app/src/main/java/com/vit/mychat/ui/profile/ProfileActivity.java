package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    public static void moveProfileActivity(Activity activity, String userId) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        activity.startActivity(intent);
    }

    @BindView(R.id.text_name)
    TextView mTextName;

    @BindView(R.id.text_status)
    TextView mTextStatus;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_background)
    ImageView mImageBackground;

    private GetUserByIdViewModel getUserByIdViewModel;
    private UpdateUserViewModel updateUserViewModel;

    private String mUserId;

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        mUserId = getIntent().getStringExtra(EXTRA_USER_ID);

        if (mUserId == null) return;

        getUserByIdViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserByIdViewModel.class);
        updateUserViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateUserViewModel.class);

        getUserByIdViewModel.getUserById(mUserId).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    loadProfile((UserViewData) resource.getData());
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    @OnClick(R.id.image_edit_cover)
    void onClickEditCover() {

    }

    @OnClick(R.id.image_edit_avatar)
    void onClickEditAvatar() {

    }

    private void loadProfile(UserViewData user) {

        mTextName.setText(user.getName());
        mTextStatus.setText(user.getStatus());

        GlideApp.with(this)
                .load(user.getAvatar())
                .circleCrop()
                .into(mImageAvatar);

        GlideApp.with(this)
                .load(user.getCover())
                .centerCrop()
                .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(mImageBackground);
    }

}
