package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.user.UpdateUserUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UpdateUserViewModel extends ViewModel {

    @Inject
    UpdateUserUseCase updateUserUseCase;

    @Inject
    UserViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> userLiveData = new SingleLiveEvent<>();

    @Inject
    public UpdateUserViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> updateUser(UserViewData userViewData) {
        userLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        updateUserUseCase.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                userLiveData.postValue(new Resource<>(ResourceState.SUCCESS, null, null));
            }

            @Override
            public void onError(Throwable e) {
                userLiveData.postValue(new Resource<>(ResourceState.ERROR, null, e));
            }
        }, UpdateUserUseCase.Params.forUpdateUser(mapper.mapFromViewData(userViewData)));

        return userLiveData;
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
