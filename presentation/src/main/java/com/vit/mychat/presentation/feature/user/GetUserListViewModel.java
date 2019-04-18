package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.user.GetUserListUseCase;
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

public class GetUserListViewModel extends ViewModel {

    @Inject
    GetUserListUseCase getUserListUseCase;

    @Inject
    UserViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> userListLiveData = new SingleLiveEvent<>();

    @Inject
    public GetUserListViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getUserList() {
        userListLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getUserListUseCase.execute(new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<User> users) {
                List<UserViewData> list = Observable.fromIterable(users)
                        .map(user -> mapper.mapToViewData(user))
                        .toList()
                        .blockingGet();
                userListLiveData.postValue(new Resource(ResourceState.SUCCESS, list, null));
            }

            @Override
            public void onError(Throwable e) {
                userListLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, null);

        return userListLiveData;
    }


    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
