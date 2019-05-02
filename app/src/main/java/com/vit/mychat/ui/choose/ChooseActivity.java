package com.vit.mychat.ui.choose;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.choose.adapter.ChooseHorizontalAdapter;
import com.vit.mychat.ui.choose.adapter.ChooseVerticalAdapter;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;
import com.vit.mychat.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseActivity extends BaseActivity implements
        OnClickChooseHorizontalItemListener, OnClickChooseVerticalItemListener {

    public static void moveChooseActivity(Activity activity) {
        Intent intent = new Intent(activity, ChooseActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.list_choose_friend_horizontal)
    RecyclerView mRcvChooseHorizon;

    @BindView(R.id.list_choose_friend_vertical)
    RecyclerView mRcvChooseVertical;

    @BindView(R.id.choose_friend)
    Toolbar mToolbar_choose;

    @BindView(R.id.text_choose_friend)
    TextView mTextCloseChoose;

    @BindView(R.id.text_ok)
    TextView mTextOk;

    @BindView(R.id.text_search)
    TextView mTextSearch;

    @Inject
    ChooseHorizontalAdapter chooseHorizontalAdapter;

    @Inject
    ChooseVerticalAdapter chooseVerticalAdapter;

    private GetFriendListViewModel getFriendListViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.choose_activity;
    }

    @Override
    protected void initView() {
        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);

        initToolbar();
        initRcvChoose();

        getFriendList();
    }

    @Override
    public void onClickChooseHorizontalItem(UserViewData userViewData) {
        chooseHorizontalAdapter.removeItem(userViewData);
        checkEmptyItem();
    }

    @Override
    public void onClickChooseVerticalItem(UserViewData userViewData, boolean isChoosed) {
        if (isChoosed) {
            chooseHorizontalAdapter.addItem(userViewData);
        } else {
            chooseHorizontalAdapter.removeItem(userViewData);
        }
        checkEmptyItem();
    }

    private void getFriendList() {
        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.FRIEND).observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    chooseVerticalAdapter.setListVertical((List<UserViewData>) resource.getData());
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private void initRcvChoose() {

        mRcvChooseVertical.setLayoutManager(new LinearLayoutManager(this));
        mRcvChooseVertical.setHasFixedSize(true);
        mRcvChooseVertical.setAdapter(chooseVerticalAdapter);

        mRcvChooseHorizon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRcvChooseHorizon.setHasFixedSize(true);
        mRcvChooseHorizon.setAdapter(chooseHorizontalAdapter);
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar_choose);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void checkEmptyItem() {
        if (chooseHorizontalAdapter.getItemCount() == 0) {
            mTextOk.setVisibility(View.INVISIBLE);
        } else {
            mTextOk.setVisibility(View.VISIBLE);
        }
    }
}
