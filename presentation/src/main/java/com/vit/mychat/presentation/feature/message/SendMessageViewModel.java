package com.vit.mychat.presentation.feature.message;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.message.SendMessageUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.message.config.MessageTypeConfig;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SendMessageViewModel extends ViewModel {

    @Inject
    SendMessageUseCase sendMessageUseCase;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> messageLiveData = new SingleLiveEvent<>();

    @Inject
    public SendMessageViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> sendMessage(String userId, String message, @MessageTypeConfig String type) {
        messageLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        sendMessageUseCase.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                messageLiveData.postValue(new Resource<>(ResourceState.SUCCESS, null, null));
            }

            @Override
            public void onError(Throwable e) {
                messageLiveData.postValue(new Resource<>(ResourceState.ERROR, null, e));
            }
        }, SendMessageUseCase.Params.forSendMessage(userId, message, type));

        return messageLiveData;
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
