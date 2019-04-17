package com.vit.mychat.ui.request_receive;

import android.app.Activity;
import android.content.Intent;

import com.vit.mychat.R;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.request_receive.adapter.RequestReceiveAdapter;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;

import javax.inject.Inject;

public class RequestReceiveActivity extends BaseActivity implements OnClickRequestReceiveItemListener {

    public static void moveRequestReceiveActivity(Activity activity) {
        Intent intent = new Intent(activity, RequestReceiveActivity.class);
        activity.startActivity(intent);
    }

    @Inject
    RequestReceiveAdapter requestReceiveAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.request_receive_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClickRequestReceiveItem(String userId) {
        ProfileActivity.moveProfileActivity(this, userId);
    }

    @Override
    public void onClickAcceptRequest(String userId) {

    }

    @Override
    public void onClickCacelRequest(String userId) {

    }
}
