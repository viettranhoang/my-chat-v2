package com.vit.mychat.ui.news;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.message.SendMessageViewModel;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.news.adapter.NewsAdapter;
import com.vit.mychat.ui.news.listener.OnClickNewsItemListener;
import com.vit.mychat.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NewsActivity extends BaseActivity implements OnClickNewsItemListener {

    public static final String EXTRA_POSITION_NEWS = "EXTRA_POSITION_NEWS";

    public static void moveNewsActivity(Activity activity, int positionNews) {
        Intent intent = new Intent(activity, NewsActivity.class);
        intent.putExtra(EXTRA_POSITION_NEWS, positionNews);
        activity.startActivity(intent);
    }

    @BindView(R.id.view_pager_news)
    ViewPager mViewPagerNews;

    @Inject
    NewsAdapter newsAdapter;

    private GetFriendListViewModel getFriendListViewModel;
    private SendMessageViewModel sendMessageViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.news_activity;
    }

    @Override
    protected void initView() {

        int positionNews = getIntent().getIntExtra(EXTRA_POSITION_NEWS, 0);

        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);
        sendMessageViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMessageViewModel.class);

        mViewPagerNews.setAdapter(newsAdapter);

        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.FRIEND)
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case SUCCESS:
                            List<UserViewData> list = (List<UserViewData>) resource.getData();
                            newsAdapter.setList(list);
                            mViewPagerNews.setCurrentItem(positionNews);
                            break;
                        case ERROR:
                            showToast(resource.getThrowable().getMessage());
                            break;
                    }
                });


    }

    @Override
    protected void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClickSend(String userId, String message) {
        sendMessageViewModel.sendMessage(userId, message)
                .observe(this, resource -> {
                    switch (resource.getStatus()) {
                        case LOADING:
                            showHUD();
                            break;
                        case SUCCESS:
                            dismissHUD();
                            break;
                        case ERROR:
                            dismissHUD();
                            showToast(resource.getThrowable().getMessage());
                            break;
                    }
                });
    }

    @Override
    public void onClickHeart() {
        showToast("heart");
    }
}
