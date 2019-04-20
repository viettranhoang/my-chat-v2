package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.vit.mychat.domain.usecase.user.GetFriendListUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetFriendListViewModel extends ViewModel {

    @Inject
    GetFriendListUseCase getFriendListUseCase;

    @Inject
    UserViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> friendListLiveData = new SingleLiveEvent<>();

    @Inject
    public GetFriendListViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getFriendList(String userId, String type) {
        friendListLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getFriendListUseCase.execute(new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<User> users) {
                Log.i("GetFriendListViewModel", "onNext: " + users.toString());
                List<UserViewData> list = Observable.fromIterable(users)
                        .map(user -> mapper.mapToViewData(user))
                        .toList()
                        .blockingGet();
                friendListLiveData.postValue(new Resource(ResourceState.SUCCESS, list, null));
            }

            @Override
            public void onError(Throwable e) {
                friendListLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, GetFriendListUseCase.Params.forGetFriendList(userId, type));

        return friendListLiveData;
    }


    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
