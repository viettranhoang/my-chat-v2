package com.vit.mychat.presentation.feature.auth;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.auth.CurrentUserUseCase;
import com.vit.mychat.domain.usecase.auth.LoginUseCase;
import com.vit.mychat.domain.usecase.auth.RegisterUseCase;
import com.vit.mychat.domain.usecase.user.model.User;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AuthViewModel extends ViewModel {

    @Inject
    LoginUseCase loginUseCase;

    @Inject
    RegisterUseCase registerUseCase;

    @Inject
    CurrentUserUseCase currentUserUseCase;

    @Inject
    UserViewDataMapper mapper;

    private SingleLiveEvent<Resource> registerLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<Resource> loginLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<Resource> userLiveData = new SingleLiveEvent<>();
    private CompositeDisposable compositeDisposable;

    @Inject
    public AuthViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }

    public MutableLiveData<Resource> login(String email, String password) {
        loginLiveData.postValue(new Resource<>(ResourceState.LOADING, null, null));
        loginUseCase.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                loginLiveData.postValue(new Resource<>(ResourceState.SUCCESS, null, null));
            }

            @Override
            public void onError(Throwable e) {
                loginLiveData.postValue(new Resource<>(ResourceState.ERROR, null, e));
            }
        }, LoginUseCase.Params.forLogin(email, password));
        return loginLiveData;
    }

    public MutableLiveData<Resource> register(String email, String password) {
        registerLiveData.postValue(new Resource<>(ResourceState.LOADING, null, null));
        registerUseCase.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                registerLiveData.postValue(new Resource<>(ResourceState.SUCCESS, null, null));
            }

            @Override
            public void onError(Throwable e) {
                registerLiveData.postValue(new Resource<>(ResourceState.ERROR, null, e));
            }
        }, RegisterUseCase.Params.forRegister(email, password));
        return registerLiveData;
    }

    public MutableLiveData<Resource> getCurrentUser() {
        userLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        currentUserUseCase.execute(new Observer<User>() {
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

        }, null);

        return userLiveData;
    }

    public void signOut() {
        loginUseCase.signOut();
    }

    public String getCurrentUserId() {
        return currentUserUseCase.getCurrentUserId();
    }
}
