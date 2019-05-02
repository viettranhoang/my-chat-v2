package com.vit.mychat.ui.choose;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vit.mychat.R;
import com.vit.mychat.presentation.feature.group.CreateGroupViewModel;
import com.vit.mychat.presentation.feature.group.model.GroupViewData;
import com.vit.mychat.presentation.feature.user.GetFriendListViewModel;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.presentation.feature.user.model.UserViewData;
import com.vit.mychat.ui.base.BaseActivity;
import com.vit.mychat.ui.choose.adapter.ChooseHorizontalAdapter;
import com.vit.mychat.ui.choose.adapter.ChooseVerticalAdapter;
import com.vit.mychat.ui.choose.listener.OnClickChooseHorizontalItemListener;
import com.vit.mychat.ui.choose.listener.OnClickChooseVerticalItemListener;
import com.vit.mychat.ui.message_group.MessageGroupActivity;
import com.vit.mychat.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ChooseActivity extends BaseActivity implements
        OnClickChooseHorizontalItemListener, OnClickChooseVerticalItemListener {

    public static void moveChooseActivity(Activity activity) {
        Intent intent = new Intent(activity, ChooseActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.list_choose_friend_horizontal)
    RecyclerView mRcvChooseHorizon;

    @BindView(R.id.list_choose_friend_vertical)
    RecyclerView mRcvChooseVertical;

    @BindView(R.id.choose_toolbar)
    Toolbar mToolbarChoose;

    @BindView(R.id.text_choose_friend)
    TextView mTextCloseChoose;

    @BindView(R.id.text_ok)
    TextView mTextOk;

    @BindView(R.id.input_search)
    EditText mInputSearch;

    @Inject
    ChooseHorizontalAdapter chooseHorizontalAdapter;

    @Inject
    ChooseVerticalAdapter chooseVerticalAdapter;

    private GetFriendListViewModel getFriendListViewModel;
    private CreateGroupViewModel createGroupViewModel;

    private PublishSubject<String> mPublishSubject;
    private CompositeDisposable compositeDisposable;
    private List<UserViewData> mSearchList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.choose_activity;
    }

    @Override
    protected void initView() {
        getFriendListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GetFriendListViewModel.class);
        createGroupViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateGroupViewModel.class);
        compositeDisposable = new CompositeDisposable();

        initToolbar();
        initRcvChoose();

        getFriendList();
    }

    @Override
    protected void onStop() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onStop();
    }

    @OnClick(R.id.text_ok)
    void onClickOk() {
        List<UserViewData> userList = chooseHorizontalAdapter.getList();
        userList.add(Constants.CURRENT_USER);

        createGroupViewModel.createGroup(userList).observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    MessageGroupActivity.moveMessageActivity(this, (GroupViewData) resource.getData());
                    finish();
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    @OnTextChanged(R.id.input_search)
    void onTextSearchChanged() {
        search(mInputSearch.getText().toString());
    }

    @Override
    public void onClickChooseHorizontalItem(UserViewData userViewData) {
        chooseHorizontalAdapter.removeItem(userViewData);
        checkEmptyItem();
    }

    @Override
    public void onClickChooseVerticalItem(UserViewData userViewData, boolean isChoosed) {
        if (isChoosed) {
            chooseHorizontalAdapter.addItem(userViewData);
        } else {
            chooseHorizontalAdapter.removeItem(userViewData);
        }
        checkEmptyItem();
    }

    private void getFriendList() {
        getFriendListViewModel.getFriendList(Constants.CURRENT_UID, UserRelationshipConfig.FRIEND).observe(this, resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    chooseVerticalAdapter.setListVertical((List<UserViewData>) resource.getData());
                    mSearchList = chooseVerticalAdapter.getList();
                    break;
                case ERROR:
                    showToast(resource.getThrowable().getMessage());
                    break;
            }
        });
    }

    private void initRcvChoose() {

        mRcvChooseVertical.setLayoutManager(new LinearLayoutManager(this));
        mRcvChooseVertical.setHasFixedSize(true);
        mRcvChooseVertical.setAdapter(chooseVerticalAdapter);

        mRcvChooseHorizon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRcvChooseHorizon.setHasFixedSize(true);
        mRcvChooseHorizon.setAdapter(chooseHorizontalAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarChoose);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void checkEmptyItem() {
        if (chooseHorizontalAdapter.getItemCount() == 0) {
            mTextOk.setVisibility(View.INVISIBLE);
        } else {
            mTextOk.setVisibility(View.VISIBLE);
        }
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
                                chooseVerticalAdapter.setListVertical(userViewDataList);
                            },
                            throwable -> showToast(throwable.getMessage()))
            );
        }

        mPublishSubject.onNext(keyword);
    }
}
