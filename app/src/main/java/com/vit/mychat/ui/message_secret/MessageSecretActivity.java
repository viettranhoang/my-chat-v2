package com.vit.mychat.ui.message_secret;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vit.mychat.R;
import com.vit.mychat.domain.usecase.secret.repository.SecretRepository;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.image.UploadImageViewModel;
import com.vit.mychat.presentation.feature.image.config.ImageTypeConfig;
import com.vit.mychat.presentation.feature.message.GetSecretMessageListViewModel;
import com.vit.mychat.presentation.feature.message.SendSecretMessageViewModel;
import com.vit.mychat.presentation.feature.message.config.MessageTypeConfig;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.message_secret.adapter.MessageSecretAdapter;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.util.DiffieHellman;
import com.vit.mychat.util.Utils;

import org.parceler.Parcels;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;

public class MessageSecretActivity extends BaseActivity {

    private static final String EXTRA_USER = "EXTRA_USER";

    public static void moveMessageSecretActivity(Activity activity, UserViewData userViewData) {
        Intent intent = new Intent(activity, MessageSecretActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USER, Parcels.wrap(userViewData));
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
    MessageSecretAdapter messageSecretAdapter;

    private GetSecretMessageListViewModel getMessageListViewModel;
    private SendSecretMessageViewModel sendMessageViewModel;
    private UploadImageViewModel uploadImageViewModel;
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

        initToolbar();

        getMessageListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetSecretMessageListViewModel.class);
        sendMessageViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendSecretMessageViewModel.class);
        uploadImageViewModel = ViewModelProviders.of(this, viewModelFactory).get(UploadImageViewModel.class);

        initRcvMessage();
        initSecretMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        diffieHellman.dispose();
    }

    @Inject
    SecretRepository secretRepository;

    @Inject
    DiffieHellman diffieHellman;

    @Inject
    MyChatFirestore myChatFirestore;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            uploadImageViewModel.uploadImage(file, ImageTypeConfig.MESSAGE).observe(this, resource -> {
                switch (resource.getStatus()) {
                    case LOADING:
                        showHUD();
                        break;
                    case SUCCESS:
                        dismissHUD();
                        String url = (String) resource.getData();
                        sendMessageViewModel.sendMessage(mUser.getId(), diffieHellman.encrypt(url), MessageTypeConfig.IMAGE)
                                .observe(this, resource1 -> {
                                    if (resource1.getStatus() == ResourceState.SUCCESS) {
                                        mRcvMessage.scrollToPosition(messageSecretAdapter.getItemCount() - 1);
                                    }
                                });
                        break;
                    case ERROR:
                        dismissHUD();
                        showToast(resource.getThrowable().getMessage());
                        break;
                }
            });
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.toolbar_message)
    void onClickToolbar() {
        ProfileActivity.moveProfileActivity(this, mUser.getId());
    }

    @OnClick(R.id.image_send)
    void onClickSend() {
        String encryptMessage = diffieHellman.encrypt(mInputMessage.getText().toString());
        Log.i(TAG, "onClickSend: " + encryptMessage);

        sendMessageViewModel.sendMessage(mUser.getId(), encryptMessage, MessageTypeConfig.TEXT)
            .observe(this, resource -> {
                if (resource.getStatus() == ResourceState.LOADING) {
                    mInputMessage.setText("");
                }
                if (resource.getStatus() == ResourceState.SUCCESS) {
                    mRcvMessage.scrollToPosition(messageSecretAdapter.getItemCount() - 1);
                }
            });
    }

    @OnClick(R.id.image_icon)
    void onClickIcon() {
        sendMessageViewModel.sendMessage(mUser.getId(), "\uD83D\uDCA9", MessageTypeConfig.TEXT)
                .observe(this, resource -> {
                    if (resource.getStatus() == ResourceState.SUCCESS) {
                        mRcvMessage.scrollToPosition(messageSecretAdapter.getItemCount() - 1);
                    }
                });
    }

    @OnClick(R.id.image_camera)
    void onClickCamera() {
        ImagePicker.Companion.with(this)
                .cameraOnly()
                .compress(500)
                .start();
    }

    @OnClick(R.id.image_picture)
    void onClickPicture() {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .compress(500)
                .start();
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

    private void initSecretMessage() {
        diffieHellman.init(mUser.getId());
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarMessage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GlideApp.with(this)
                .load(mUser.getAvatar())
                .circleCrop()
                .placeholder(R.drawable.ic_avatar_user)
                .into(mImageAvatar);

        mTextName.setText(mUser.getName());

        if (mUser.getOnline() == 1) {
            mImageOnline.setVisibility(View.VISIBLE);
            mTextOnline.setText(getString(R.string.dang_hoat_dong));
        } else {
            mImageOnline.setVisibility(View.INVISIBLE);
            mTextOnline.setText(Utils.getTime(mUser.getOnline()));
        }
    }

    private void initRcvMessage() {
        mRcvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRcvMessage.setHasFixedSize(true);
        mRcvMessage.setItemAnimator(new DefaultItemAnimator());
        mRcvMessage.setAdapter(messageSecretAdapter);

        getMessageListViewModel.getMessageList(mUser.getId()).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<MessageViewData> messages = decryptMessageList((List<MessageViewData>) resource.getData());
                    Log.i(TAG, "initRcvMessage: " + messages);
                    messageSecretAdapter.setList(messages);
                    mRcvMessage.scrollToPosition(messageSecretAdapter.getItemCount() - 1);
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private List<MessageViewData> decryptMessageList(List<MessageViewData> messages) {
        return Observable.fromIterable(messages)
                .map(messageViewData -> {
                    messageViewData.setMessage(diffieHellman.decrypt(messageViewData.getMessage()));
                    return messageViewData;
                })
                .toList()
                .blockingGet();
    }
}
