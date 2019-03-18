package com.vit.mychat.ui.chat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseFragment;
import com.vit.mychat.util.GlideApp;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

public class ChatFragment extends BaseFragment {

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_one)
    ImageView mImageCamera;

    @BindView(R.id.image_two)
    ImageView mImageCreate;

    @BindView(R.id.text_title)
    TextView mTextTitle;

    @BindDrawable(R.drawable.ic_camera_black)
    Drawable mIcCamera;

    @BindDrawable(R.drawable.ic_create_black)
    Drawable mIcCreate;

    @Override
    public int getLayoutId() {
        return R.layout.chat_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
    }

    @OnClick(R.id.image_avatar)
    void onClickAvatar() {
        Toast.makeText(mainActivity, "avatar", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.image_one)
    void onClickCamera() {
        Toast.makeText(mainActivity, "camera", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.image_two)
    void onClickCreate() {
        Toast.makeText(mainActivity, "create", Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        mTextTitle.setText(getString(R.string.chat));
        mImageCamera.setImageDrawable(mIcCamera);
        mImageCreate.setImageDrawable(mIcCreate);


        GlideApp.with(this)
                .load("http://gnemart.com/wp-content/uploads/2018/10/gau-bong-quobee-1.jpg")
                .circleCrop()
                .into(mImageAvatar);


    }
}
