package com.vit.mychat.ui.message_group;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.message.GetMessageListViewModel;
import com.vit.mychat.presentation.feature.message.SendMessageViewModel;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.message_group.adapter.MessageGroupAdapter;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MessageGroupActivity extends BaseActivity {

    private static final String EXTRA_GROUP = "EXTRA_GROUP";

    public static void moveMessageActivity(Activity activity, GroupViewData groupViewData) {
        Intent intent = new Intent(activity, MessageGroupActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_GROUP, Parcels.wrap(groupViewData));
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar_message)
    Toolbar mToolbarMessage;

    @BindView(R.id.text_name)
    TextView mTextName;

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
    MessageGroupAdapter messageAdapter;

    private GetMessageListViewModel getMessageListViewModel;
    private SendMessageViewModel sendMessageViewModel;
    private GroupViewData mGroup;


    @Override
    protected int getLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    protected void initView() {
        mGroup = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_GROUP));

        if (mGroup == null)
            return;

        initToolbar();

        getMessageListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetMessageListViewModel.class);
        sendMessageViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMessageViewModel.class);

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

    @OnClick(R.id.image_send)
    void onClickSend() {
        sendMessageViewModel.sendMessage(mGroup.getId(), mInputMessage.getText().toString())
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
        mInputMessage.setText("");
    }

    @OnClick(R.id.image_icon)
    void onClickIcon() {
        showToast("Icon");
    }

    @OnTextChanged(R.id.input_message)
    void onTextChangeMessage() {
        if (!TextUtils.isEmpty(mInputMessage.getText())) {
            mImageIcon.setVisibility(View.INVISIBLE);
            mImageSend.setVisibility(View.VISIBLE);
        } else {
            mImageIcon.setVisibility(View.VISIBLE);
            mImageSend.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarMessage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GlideApp.with(this)
                .load(mGroup.getAvatar())
                .circleCrop()
                .into(mImageAvatar);

        mTextName.setText(mGroup.getName());
        mImageOnline.setVisibility(View.INVISIBLE);
        mTextOnline.setVisibility(View.GONE);
    }

    private void initRcvMessage() {
        messageAdapter.setGroup(mGroup);
        mRcvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRcvMessage.setHasFixedSize(true);
        mRcvMessage.setItemAnimator(new DefaultItemAnimator());
        mRcvMessage.setAdapter(messageAdapter);

        getMessageListViewModel.getMessageList(mGroup.getId()).observe(this, resource -> {
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
