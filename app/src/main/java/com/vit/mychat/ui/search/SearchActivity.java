package com.vit.mychat.ui.search;

import android.app.Activity;
import android.content.Intent;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.search.adapter.SearchAdapter;
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity implements OnClickSearchItemListener {

    public static void moveSearchActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Inject
    SearchAdapter searchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClickSearchItem(UserViewData userViewData) {

    }
}
