package com.vit.mychat.ui.message;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.data.model.Message1;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.base.module.GlideApp;
import com.vit.mychat.ui.message.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {

    public static void moveMessageActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MessageActivity.class));
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

    @BindView(R.id.input_text)
    EditText mInputText;

    @BindView(R.id.image_icon)
    ImageView mImageIcon;

    @BindView(R.id.image_online)
    ImageView mImageOnline;

    @BindView(R.id.text_online)
    TextView mTextOnline;

    private MessageAdapter mMessageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.message_activity;
    }


    @Override
    protected void initView() {
        initToolbar();
        initRcvMessage();

        GlideApp.with(this)
                .load("https://product.hstatic.net/1000069863/product/qoobee_ng_i__m_b_nh_01010433_1tr239k_master.jpg")
                .circleCrop()
                .into(mImageAvatar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarMessage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void initRcvMessage() {
        mMessageAdapter = new MessageAdapter(creatListMess());
        mRcvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRcvMessage.setHasFixedSize(true);
        mRcvMessage.setAdapter(mMessageAdapter);
    }

    private List<Message1> creatListMess() {
        List<Message1> mList = new ArrayList<>();
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "đi chơi với tớ không, hay là đi ăn đi ^^", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "éo có tiền mà cơ, cậu bao tớ nhé hí hí ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "cậu đang làm gì thế ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "cậu đang làm gì thế ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "cậu đang làm gì thế ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", "đồ con gà này :v ", true, "12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg", ".", true, "12:28"));

        return mList;
    }


}
