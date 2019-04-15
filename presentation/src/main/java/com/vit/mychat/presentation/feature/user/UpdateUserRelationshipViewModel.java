package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.user.UpdateUserRelationshipUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UpdateUserRelationshipViewModel extends ViewModel {

    @Inject
    UpdateUserRelationshipUseCase updateUserRelationshipUseCase;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> userLiveData = new SingleLiveEvent<>();

    @Inject
    public UpdateUserRelationshipViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> updateUserRelationship(String fromId, String toId, @UserRelationshipConfig String type) {
        userLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        updateUserRelationshipUseCase.execute(new CompletableObserver() {
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
        }, UpdateUserRelationshipUseCase.Params.forUpdateRelationship(fromId, toId, type));

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
