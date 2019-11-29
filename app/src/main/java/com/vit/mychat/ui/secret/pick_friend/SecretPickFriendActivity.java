package com.vit.mychat.ui.secret.pick_friend;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.message_secret.MessageSecretActivity;
import com.vit.mychat.ui.profile.ProfileActivity;
import com.vit.mychat.ui.secret.pick_friend.adapter.SecretPickFriendAdapter;
import com.vit.mychat.ui.secret.pick_friend.listener.OnClickSecretItemListener;
import com.vit.mychat.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SecretPickFriendActivity extends BaseActivity implements OnClickSecretItemListener {

    public static void moveSecretPickFriendActivity(Activity activity) {
        Intent intent = new Intent(activity, SecretPickFriendActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearchUser;

    @BindView(R.id.list_search_user)
    RecyclerView mRcvSearch;

    @BindView(R.id.text_search)
    EditText mInputSearch;

    @Inject
    SecretPickFriendAdapter secretPickFriendAdapter;

    private PublishSubject<String> mPublishSubject;
    private CompositeDisposable compositeDisposable;
    private GetFriendListViewModel getFriendListViewModel;
    private List<UserViewData> mSearchList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRcvSearchUser();

        compositeDisposable = new CompositeDisposable();
        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);

        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.FRIEND)
                .observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    showHUD();
                    break;
                case SUCCESS:
                    dismissHUD();
                    mSearchList = (List<UserViewData>) resource.getData();
                    secretPickFriendAdapter.setList(mSearchList);
                    break;
                case ERROR:
                    dismissHUD();
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    @Override
    protected void onStop() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onStop();
    }

    @Override
    public void onClickSearchItem(UserViewData userViewData) {
        MessageSecretActivity.moveMessageSecretActivity(this, userViewData);
    }

    @Override
    public void onClickInfo(String userId) {
        ProfileActivity.moveProfileActivity(this, userId);
    }

    @OnTextChanged(R.id.text_search)
    void onTextSearchChanged() {
        search(mInputSearch.getText().toString());
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarSearchUser);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initRcvSearchUser() {
        mRcvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRcvSearch.setHasFixedSize(true);
        mRcvSearch.setItemAnimator(new DefaultItemAnimator());
        mRcvSearch.setAdapter(secretPickFriendAdapter);
    }

    private void search(String keyword) {
        if (mPublishSubject == null) {
            mPublishSubject = PublishSubject.create();
            compositeDisposable.add(mPublishSubject
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map(text -> text.trim())
                    .distinctUntilChanged()
                    .switchMap(text ->
                            Observable.fromIterable(mSearchList)
                                    .filter(userViewData -> userViewData.getName().toLowerCase().contains(text.toLowerCase()))
                                    .toList()
                                    .toObservable())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userViewDataList -> {
                                secretPickFriendAdapter.setList(userViewDataList);
                            },
                            throwable -> showToast(throwable.getMessage()))
            );
        }

        mPublishSubject.onNext(keyword);
    }




}
