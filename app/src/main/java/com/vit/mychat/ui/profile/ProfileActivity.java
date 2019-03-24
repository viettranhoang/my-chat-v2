package com.vit.mychat.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vit.mychat.R;
import com.vit.mychat.data.model.User;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.util.Constant;
import com.vit.mychat.util.GlideApp;
import com.vit.mychat.util.RoundedCornersTransformation;

import butterknife.BindView;

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

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot;

    private String mUserId = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.profile_activity;
    }

    @Override
    protected void initView() {
        initFirebase();

        mRoot.child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                loadProfile(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast(databaseError.getMessage());
            }
        });

    }

    private void loadProfile(User user) {

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
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference(Constant.TABLE_USER);
    }
}
