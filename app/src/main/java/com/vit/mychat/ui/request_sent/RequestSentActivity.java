package com.vit.mychat.ui.request_sent;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetUserListViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.request_sent.adapter.RequestSentAdapter;
import com.vit.mychat.ui.request_sent.listener.OnClickRequestSentItemListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RequestSentActivity extends BaseActivity implements OnClickRequestSentItemListener {

    private GetUserListViewModel getUserListViewModel;

    @BindView(R.id.list_sent_user)
    RecyclerView mListUser;

    @BindView(R.id.toolbar_sent_user)
    Toolbar mToolbarSentUser;

    @Inject
    RequestSentAdapter requestSentAdapter; // t có tạo sẵn adap tẻ cho c đaay còn j

    public static void moveRequestSentActivity(Activity activity) {
        Intent intent = new Intent(activity, RequestSentActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.request_sent_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRcvSentUser();

        getUserListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserListViewModel.class);

        getUserListViewModel.getUserList().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<UserViewData> list = (List<UserViewData>) resource.getData();

                    requestSentAdapter.setRequestSent(list);

                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(mToolbarSentUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initRcvSentUser() {
        mListUser.setLayoutManager(new LinearLayoutManager(this));
        mListUser.setHasFixedSize(true);
        mListUser.setAdapter(requestSentAdapter);

    }


    @Override
    public void onClickRequestSentItem(String userId) {
        ProfileActivity.moveProfileActivity(this, userId);
    }

    @Override
    public void onClickCacelRequest(String userId) {

    }
}