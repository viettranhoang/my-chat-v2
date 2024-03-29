package com.vit.mychat.ui.request_sent;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.vit.mychat.ui.request_sent.adapter.RequestSentAdapter;
import com.vit.mychat.ui.request_sent.listener.OnClickRequestSentItemListener;
import com.vit.mychat.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RequestSentActivity extends BaseActivity implements OnClickRequestSentItemListener {

    @BindView(R.id.list_sent_user)
    RecyclerView mRcvRequestSent;

    @BindView(R.id.toolbar_request_sent)
    Toolbar mToolbarSentUser;

    @Inject
    RequestSentAdapter requestSentAdapter;

    private UpdateUserRelationshipViewModel updateUserRelationshipViewModel;
    private GetFriendListViewModel getFriendListViewModel;

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
        initRcvRequestSent();

        updateUserRelationshipViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateUserRelationshipViewModel.class);
        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getListRequestSent();
    }

    @Override
    public void onClickRequestSentItem(UserViewData user) {
        ProfileActivity.moveProfileActivity(this, user.getId());
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
                            requestSentAdapter.deleteUser(user);
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

    private void initRcvRequestSent() {
        mRcvRequestSent.setLayoutManager(new LinearLayoutManager(this));
        mRcvRequestSent.setHasFixedSize(true);
        mRcvRequestSent.setItemAnimator(new DefaultItemAnimator());
        mRcvRequestSent.setAdapter(requestSentAdapter);
    }

    private void getListRequestSent() {
        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.SENT).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<UserViewData> list = (List<UserViewData>) resource.getData();
                    requestSentAdapter.setList(list);
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }


}