package com.vit.mychat.ui.search;

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
import com.vit.mychat.ui.search.adapter.SearchAdapter;
import com.vit.mychat.ui.search.listener.OnClickSearchItemListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements OnClickSearchItemListener {

    public static void moveSearchActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    private GetUserListViewModel getUserListViewModel;

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearchUser;

    @BindView(R.id.list_search_user)
    RecyclerView mListUserSearch;

    @Inject
    SearchAdapter searchAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRcvSearchUser();

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

                    searchAdapter.setSetSearchUser(list);

                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarSearchUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initRcvSearchUser() {
        mListUserSearch.setLayoutManager(new LinearLayoutManager(this));
        mListUserSearch.setHasFixedSize(true);
        mListUserSearch.setAdapter(searchAdapter);
    }

    @Override
    public void onClickSearchItem(UserViewData userViewData) {

    }
}
