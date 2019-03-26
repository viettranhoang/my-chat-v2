package com.vit.mychat.ui.message;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.data.model.Message1;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.message.adapter.MessageAdapter;
import com.vit.mychat.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {

    public static void moveMessageActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MessageActivity.class));
    }

    private MessageAdapter mMessageAdapter;


    @BindView(R.id.list_mess)
    RecyclerView mRcvMessage;

    @BindView(R.id.image_avatar)
    ImageView mImageAvatar;

    @BindView(R.id.ic_call)
    ImageView mImageCall;

    @BindView(R.id.ic_call_video)
    ImageView mImageCallVideo;

    @BindView(R.id.ic_more)
    ImageView mImageMore;

    @BindView(R.id.ic_camera)
    ImageView mImageCamera;

    @BindView(R.id.ic_picture)
    ImageView mImagePicture;

    @BindView(R.id.ic_mic)
    ImageView mImageMic;

    @BindView(R.id.input_text)
    EditText mInputText;

    @BindView(R.id.ic_icon)
    ImageView mImageIcon;

    @BindView(R.id.image_online)
    ImageView mImageOnline;

    @BindView(R.id.text_status)
    TextView mTextSttatus;

    @BindView(R.id.ic_back)
    ImageView mImageBack;


    @Override
    protected int getLayoutId() {

        return R.layout.message_activity;

    }


    @Override
    protected void initView() {
        mMessageAdapter = new MessageAdapter(creatListMess());
        mRcvMessage.setLayoutManager(new LinearLayoutManager(this));
        mRcvMessage.setHasFixedSize(true);
        mRcvMessage.setAdapter(mMessageAdapter);


        GlideApp.with(this)
                .load("https://product.hstatic.net/1000069863/product/qoobee_ng_i__m_b_nh_01010433_1tr239k_master.jpg")
                .circleCrop()
                .into(mImageAvatar);




    }

    private List<Message1> creatListMess() {
        List<Message1> mList = new ArrayList<>();
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế cậu đang làm gì thế ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","đi chơi với tớ không, hay là đi ăn đi ^^",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","éo có tiền mà cơ, cậu bao tớ nhé hí hí ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","cậu đang làm gì thế ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","cậu đang làm gì thế ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","cậu đang làm gì thế ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg","đồ con gà này :v ",true,"12:28"));
        mList.add(new Message1("https://kenh14cdn.com/2017/1-1500543356729.jpg",".",true,"12:28"));

        return mList;
    }


}
