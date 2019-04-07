package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetUserByIdViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

    public static void moveProfileActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
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

//    private FirebaseDatabase mDatabase;
//    private DatabaseReference mRoot;

    private String mUserId = "F9u5S8Z9SBzZ0GNCOqGM";

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        getUserByIdViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserByIdViewModel.class);

        getUserByIdViewModel.getUserById(mUserId).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    break;
                case SUCCESS:
                    loadProfile((UserViewData) resource.getData());
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
        initFirebase();

        /*mRoot.child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                loadProfile(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast(databaseError.getMessage());
            }
        });*/
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

    private void initFirebase() {
//        mDatabase = FirebaseDatabase.getInstance();
//        mRoot = mDatabase.getReference(Constant.TABLE_USER);
    }
}
