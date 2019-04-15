package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.user.GetUserRelationshipUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.config.UserRelationshipConfig;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetUserRelationshipViewModel extends ViewModel {

    @Inject
    GetUserRelationshipUseCase getUserRelationshipUseCase;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> userLiveData = new SingleLiveEvent<>();

    @Inject
    public GetUserRelationshipViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> getUserRelationship(String fromId, String toId) {
        userLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getUserRelationshipUseCase.execute(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                @UserRelationshipConfig String relationship;

                if (s.equals(UserRelationshipConfig.FRIEND))
                    relationship = UserRelationshipConfig.FRIEND;
                else if (s.equals(UserRelationshipConfig.SENT))
                    relationship = UserRelationshipConfig.SENT;
                else if (s.equals(UserRelationshipConfig.RECEIVE))
                    relationship = UserRelationshipConfig.RECEIVE;
                else
                    relationship = UserRelationshipConfig.NOT;

                userLiveData.postValue(new Resource(ResourceState.SUCCESS, relationship, null));
            }

            @Override
            public void onError(Throwable e) {
                userLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, GetUserRelationshipUseCase.Params.forRelationship(fromId, toId));

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
