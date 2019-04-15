package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.auth.AuthViewModel;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;
import com.vit.mychat.presentation.feature.user.GetUserRelationshipViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.MainActivity;
import com.vit.mychat.ui.auth.AuthActivity;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
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

    @BindView(R.id.image_call)
    ImageView mImageCall;

    @BindView(R.id.text_call)
    TextView mTextCall;

    @BindView(R.id.image_message)
    ImageView mImageMessage;

    @BindView(R.id.text_message)
    TextView mTextMessage;

    @BindView(R.id.image_add_friend)
    ImageView mImageAddFriend;

    @BindView(R.id.text_add_friend)
    TextView mTextAddFriend;

    @BindView(R.id.switch_online)
    Switch mSwitchOnline;

    @BindView(R.id.image_logout)
    ImageView mImageLogout;

    private GetUserByIdViewModel getUserByIdViewModel;
    private UpdateUserViewModel updateUserViewModel;
    private AuthViewModel authViewModel;
    private GetUserRelationshipViewModel getUserRelationshipViewModel;

    private String mUserId;
    private UserViewData mUserViewData;

    @UserRelationshipConfig
    String mCurrentRelationship;

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
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel.class);
        getUserRelationshipViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserRelationshipViewModel.class);

        loadUI();

        getUserByIdViewModel.getUserById(mUserId).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    mUserViewData = (UserViewData) resource.getData();
                    loadProfile(mUserViewData);
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });

    }

    @Override
    public void onBackPressed() {
        MainActivity.moveMainActivity(this);
    }

    @OnCheckedChanged(R.id.switch_online)
    void onCheckedOnline() {
        if (mUserViewData != null) {
            mUserViewData.setOnline(mSwitchOnline.isChecked() ? 1 : System.currentTimeMillis());
            updateUserViewModel.updateUser(mUserViewData);
        }
    }

    @OnClick(R.id.image_logout)
    void onClickLogout() {
        authViewModel.signOut();
        AuthActivity.moveAuthActivity(this);
        finish();
    }

    @OnClick(R.id.image_edit_cover)
    void onClickEditCover() {

    }

    @OnClick(R.id.image_edit_avatar)
    void onClickEditAvatar() {

    }

    @OnClick(R.id.image_add_friend)
    void onClickAddFriend() {
        if (!TextUtils.isEmpty(mCurrentRelationship)) {
            switch (mCurrentRelationship) {
                case UserRelationshipConfig.FRIEND:

                    break;
                case UserRelationshipConfig.NOT:
                    break;
                case UserRelationshipConfig.SENT:
                    break;
                case UserRelationshipConfig.RECEIVE:
                    break;
            }
        }
    }



    private void getRelationship() {
        getUserRelationshipViewModel.getUserRelationship(authViewModel.getCurrentUserId(), mUserId).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    mCurrentRelationship = (String) resource.getData();
                    switch (mCurrentRelationship) {
                        case UserRelationshipConfig.NOT:
                            mImageAddFriend.setImageResource(R.drawable.ic_add_friends);
                            mTextAddFriend.setText(getString(R.string.them_ban_be));
                            mTextAddFriend.setTextColor(getResources().getColor(R.color.black87));
                            break;
                        case UserRelationshipConfig.FRIEND:
                            mImageAddFriend.setImageResource(R.drawable.ic_person);
                            mTextAddFriend.setText(getString(R.string.huy_ket_ban));
                            mTextAddFriend.setTextColor(getResources().getColor(R.color.blue));
                            break;
                        case UserRelationshipConfig.SENT:
                            mImageAddFriend.setImageResource(R.drawable.ic_add_friends);
                            mTextAddFriend.setText(getString(R.string.huy_loi_moi));
                            mTextAddFriend.setTextColor(getResources().getColor(R.color.black87));
                            break;
                        case UserRelationshipConfig.RECEIVE:
                            mImageAddFriend.setImageResource(R.drawable.ic_add_friends);
                            mTextAddFriend.setText(getString(R.string.tra_loi));
                            mTextAddFriend.setTextColor(getResources().getColor(R.color.black87));
                            break;
                    }
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private void loadUI() {
        if (authViewModel.getCurrentUserId().equals(mUserId)) {
            mImageCall.setVisibility(View.GONE);
            mTextCall.setText(R.string.dang_xuat);
            mImageMessage.setVisibility(View.GONE);
            mTextMessage.setText(R.string.online);
            mSwitchOnline.setVisibility(View.VISIBLE);
            mImageLogout.setVisibility(View.VISIBLE);
        } else {
            getRelationship();
        }
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

        mSwitchOnline.setChecked(user.getOnline() == 1);
    }

}
