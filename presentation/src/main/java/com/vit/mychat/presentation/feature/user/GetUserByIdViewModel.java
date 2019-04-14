package com.vit.mychat.presentation.feature.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.user.GetUserByIdUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetUserByIdViewModel extends ViewModel {

    @Inject
    GetUserByIdUseCase getUserByIdUseCase;

    @Inject
    UserViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> userLiveData = new SingleLiveEvent<>();

    @Inject
    public GetUserByIdViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getUserById(String id) {
        userLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

       getUserByIdUseCase.execute(new Observer<User>() {
           @Override
           public void onSubscribe(Disposable d) {
               compositeDisposable.add(d);
           }

           @Override
           public void onNext(User user) {
               userLiveData.postValue(new Resource(ResourceState.SUCCESS, mapper.mapToViewData(user), null));
           }

           @Override
           public void onError(Throwable e) {
                userLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
           }

           @Override
           public void onComplete() {
           }

       }, GetUserByIdUseCase.Params.forUserById(id));

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
