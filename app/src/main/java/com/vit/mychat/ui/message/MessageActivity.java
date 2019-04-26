package com.vit.mychat.ui.message;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.message.GetMessageListViewModel;
import com.vit.mychat.presentation.feature.message.SendMessageViewModel;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.message.adapter.MessageAdapter;
import com.vit.mychat.ui.profile.ProfileActivity;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

    private static final String EXTRA_USER = "EXTRA_USER";

    public static void moveMessageActivity(Activity activity, UserViewData userViewData) {
        Intent intent = new Intent(activity, MessageActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USER, Parcels.wrap(userViewData));
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar_message)
    Toolbar mToolbarMessage;

    @BindView(R.id.list_mess)
    RecyclerView mRcvMessage;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.image_camera)
    ImageView mImageCamera;

    @BindView(R.id.image_picture)
    ImageView mImagePicture;

    @BindView(R.id.image_mic)
    ImageView mImageMic;

    @BindView(R.id.input_message)
    EditText mInputMessage;

    @BindView(R.id.image_icon)
    ImageView mImageIcon;

    @BindView(R.id.image_online)
    ImageView mImageOnline;

    @BindView(R.id.text_online)
    TextView mTextOnline;

    @BindView(R.id.image_send)
    ImageView mImageSend;

    @Inject
    MessageAdapter messageAdapter;

    private GetMessageListViewModel getMessageListViewModel;
    private SendMessageViewModel sendMessageViewModel;
    private UserViewData mUser;


    @Override
    protected int getLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    protected void initView() {
        mUser = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_USER));

        if (mUser == null)
            return;

        getMessageListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetMessageListViewModel.class);
        sendMessageViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMessageViewModel.class);

        initToolbar();
        initRcvMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_call:
                showToast("call");
                break;
            case R.id.menu_video_call:
                showToast("video call");
                break;
            case R.id.menu_more:
                showToast("more");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.toolbar_message)
    void onClickToolbar() {
        ProfileActivity.moveProfileActivity(this, mUser.getId());
    }

    @OnClick(R.id.image_send)
    void onClickSend() {
        sendMessageViewModel.sendMessage(mUser.getId(), mInputMessage.getText().toString())
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case LOADING:
                            showHUD();
                            break;
                        case SUCCESS:
                            dismissHUD();
                            break;
                        case ERROR:
                            dismissHUD();
                            showToast(resource.getThrowable().getMessage());
                            break;
                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarMessage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GlideApp.with(this)
                .load(mUser.getAvatar())
                .circleCrop()
                .into(mImageAvatar);

        if (mUser.getOnline() == 1) {
            mImageOnline.setVisibility(View.VISIBLE);
            mTextOnline.setText(getString(R.string.dang_hoat_dong));
        } else {
            mImageOnline.setVisibility(View.INVISIBLE);
            mTextOnline.setText(String.valueOf(mUser.getOnline()));
        }
    }

    private void initRcvMessage() {
        messageAdapter.setUser(mUser);
        mRcvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRcvMessage.setHasFixedSize(true);
        mRcvMessage.setAdapter(messageAdapter);

        getMessageListViewModel.getMessageList().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    messageAdapter.setList((List<MessageViewData>) resource.getData());
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }


}
