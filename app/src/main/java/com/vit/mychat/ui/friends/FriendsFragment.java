package com.vit.mychat.ui.friends;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.image.UploadImageViewModel;
import com.vit.mychat.presentation.feature.image.config.ImageTypeConfig;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.UpdateUserViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseFragment;
import com.vit.mychat.ui.friends.adapter.FriendNewsAdapter;
import com.vit.mychat.ui.friends.adapter.FriendOnlineAdapter;
import com.vit.mychat.ui.friends.listener.OnClickFriendNewsItemListener;
import com.vit.mychat.ui.friends.listener.OnClickFriendOnlineItemListener;
import com.vit.mychat.ui.message.MessageActivity;
import com.vit.mychat.ui.news.NewsActivity;
import com.vit.mychat.util.Constants;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FriendsFragment extends BaseFragment
        implements OnClickFriendNewsItemListener, OnClickFriendOnlineItemListener {

    public static final String TAG = FriendsFragment.class.getSimpleName();

    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.list_friend_news)
    RecyclerView mRcvFriendNews;

    @BindView(R.id.list_friend_online)
    RecyclerView mRcvFriendOnline;

    @Inject
    FriendNewsAdapter friendNewsAdapter;

    @Inject
    FriendOnlineAdapter friendOnlineAdapter;

    private GetFriendListViewModel getFriendListViewModel;
    private UploadImageViewModel uploadImageViewModel;
    private UpdateUserViewModel updateUserViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.friends_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);
        uploadImageViewModel = ViewModelProviders.of(this, viewModelFactory).get(UploadImageViewModel.class);
        updateUserViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpdateUserViewModel.class);

        initRcv();
        getFriendList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mainActivity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            updateNewsImage(file);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFriendList() {
        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.FRIEND).observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    List<UserViewData> list = (List<UserViewData>) resource.getData();
                    friendNewsAdapter.setList(list);
                    friendOnlineAdapter.setList(list);
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private void initRcv() {
        mRcvFriendOnline.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        mRcvFriendOnline.setHasFixedSize(true);
        mRcvFriendNews.setItemAnimator(new DefaultItemAnimator());
        mRcvFriendOnline.setAdapter(friendOnlineAdapter);

        mRcvFriendNews.setLayoutManager(new GridLayoutManager(mainActivity, 2));
        mRcvFriendNews.setHasFixedSize(true);
        mRcvFriendOnline.setItemAnimator(new DefaultItemAnimator());
        mRcvFriendNews.setAdapter(friendNewsAdapter);
    }

    @Override
    public void onClickFriendNewsItem(int position) {
        if (position == 0) {
            ImagePicker.Companion.with(this)
                    .maxResultSize(1080, 1920)
                    .compress(1024)
                    .start();

            return;
        }
        NewsActivity.moveNewsActivity(mainActivity, --position);
    }

    @Override
    public void onClickFriendOnlineItem(UserViewData userViewData) {
        MessageActivity.moveMessageActivity(mainActivity, userViewData);
    }

    private void updateNewsImage(File image) {
        uploadImageViewModel.uploadImage(image, ImageTypeConfig.NEWS).observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    mainActivity.showHUD();
                    break;
                case SUCCESS:
                    mainActivity.dismissHUD();
                    String imageUrl = (String) resource.getData();
                    Constants.CURRENT_USER.setNews(imageUrl);
                    updateUserViewModel.updateUser(Constants.CURRENT_USER);

                    break;
                case ERROR:
                    mainActivity.dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }
}
