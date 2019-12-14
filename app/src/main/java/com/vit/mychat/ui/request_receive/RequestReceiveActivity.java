package com.vit.mychat.ui.request_receive;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserRelationshipViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.request_receive.adapter.RequestReceiveAdapter;
import com.vit.mychat.ui.request_receive.listener.OnClickRequestReceiveItemListener;
import com.vit.mychat.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RequestReceiveActivity extends BaseActivity implements OnClickRequestReceiveItemListener {

    @BindView(R.id.list_receive_user)
    RecyclerView mListReceiveUser;

    @BindView(R.id.toolbar_request_receive)
    Toolbar mToolbarReceiveUser;

    @Inject
    RequestReceiveAdapter requestReceiveAdapter;

    private UpdateUserRelationshipViewModel updateUserRelationshipViewModel;
    private GetFriendListViewModel getFriendListViewModel;

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

        updateUserRelationshipViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateUserRelationshipViewModel.class);
        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getListRequestReceive();
    }

    @Override
    public void onClickRequestReceiveItem(UserViewData user) {
        ProfileActivity.moveProfileActivity(this, user.getId());
    }

    @Override
    public void onClickAcceptRequest(UserViewData user) {
        updateUserRelationshipViewModel.updateUserRelationship(Constants.CURRENT_UID, user.getId(), UserRelationshipConfig.FRIEND)
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case LOADING:
                            showHUD();
                            break;
                        case SUCCESS:
                            dismissHUD();
                            requestReceiveAdapter.deleteUser(user);
                            break;
                        case ERROR:
                            dismissHUD();
                            showToast(resource.getThrowable().getMessage());
                            break;
                    }
                });
    }

    @Override
    public void onClickCancelRequest(UserViewData user) {
        updateUserRelationshipViewModel.updateUserRelationship(Constants.CURRENT_UID, user.getId(), UserRelationshipConfig.NOT)
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case LOADING:
                            showHUD();
                            break;
                        case SUCCESS:
                            dismissHUD();
                            requestReceiveAdapter.deleteUser(user);
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

    private void getListRequestReceive() {
        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.RECEIVE).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<UserViewData> list = (List<UserViewData>) resource.getData();
                    requestReceiveAdapter.setList(list);
                    break;
                case ERROR:
                    dismissHUD();
                    break;
            }
        });
    }


}
