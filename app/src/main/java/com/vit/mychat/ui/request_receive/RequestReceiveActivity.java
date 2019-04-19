package com.vit.mychat.ui.request_receive;

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
import com.vit.mychat.ui.request_receive.adapter.RequestReceiveAdapter;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RequestReceiveActivity extends BaseActivity implements OnClickRequestReceiveItemListener {

    private GetUserListViewModel getUserListViewModel;

    @BindView(R.id.list_receive_user)
    RecyclerView mListReceiveUser;

    @BindView(R.id.toolbar_receive_user)
    Toolbar mToolbarReceiveUser;

    @Inject
    RequestReceiveAdapter requestReceiveAdapter;

    public static void moveRequestReceiveActivity(Activity activity) {
        Intent intent = new Intent(activity, RequestReceiveActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.request_receive_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRcvReceiveUser();

        getUserListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserListViewModel.class);

        getUserListViewModel.getUserList().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<UserViewData> list = (List<UserViewData>) resource.getData();

                    requestReceiveAdapter.setRequestReceive(list);

                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });


    }

    private void initToolbar() {
        setSupportActionBar(mToolbarReceiveUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initRcvReceiveUser() {
        mListReceiveUser.setLayoutManager(new LinearLayoutManager(this));
        mListReceiveUser.setHasFixedSize(true);
        mListReceiveUser.setAdapter(requestReceiveAdapter);
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
