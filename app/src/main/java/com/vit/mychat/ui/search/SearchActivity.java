package com.vit.mychat.ui.search;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetUserListViewModel;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.search.adapter.SearchAdapter;
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import java.util.List;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity implements OnClickSearchItemListener {

    public static void moveSearchActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Inject
    SearchAdapter searchAdapter;

    private GetUserListViewModel getUserListViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {

        getUserListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetUserListViewModel.class);

        getUserListViewModel.getUserList().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    List<UserViewData> list = (List<UserViewData>) resource.getData();

                    // Set List cho adapter o day :))

                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    @Override
    public void onClickSearchItem(UserViewData userViewData) {

    }
}
