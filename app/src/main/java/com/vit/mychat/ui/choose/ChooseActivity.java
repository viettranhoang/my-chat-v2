package com.vit.mychat.ui.choose;

import android.app.Activity;
import android.content.Intent;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.choose.adapter.ChooseHorizontalAdapter;
import com.vit.mychat.ui.choose.adapter.ChooseVerticalAdapter;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;

import javax.inject.Inject;

public class ChooseActivity extends BaseActivity implements
        OnClickChooseHorizontalItemListener, OnClickChooseVerticalItemListener {

    public static void moveChooseActivity(Activity activity) {
        Intent intent = new Intent(activity, ChooseActivity.class);
        activity.startActivity(intent);
    }

    @Inject
    ChooseHorizontalAdapter chooseHorizontalAdapter;

    @Inject
    ChooseVerticalAdapter chooseVerticalAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.choose_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClickChooseHorizontalItem(UserViewData userViewData) {

    }

    @Override
    public void onClickChooseVerticalItem(UserViewData userViewData) {

    }
}
