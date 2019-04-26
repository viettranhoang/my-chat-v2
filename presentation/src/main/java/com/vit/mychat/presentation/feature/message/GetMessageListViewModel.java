package com.vit.mychat.presentation.feature.message;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.message.GetMessageListUseCase;
import com.vit.mychat.domain.usecase.message.model.Message;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.message.mapper.MessageViewDataMapper;
import com.vit.mychat.presentation.feature.message.model.MessageViewData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetMessageListViewModel extends ViewModel {

    @Inject
    GetMessageListUseCase getMessageListUseCase;

    @Inject
    MessageViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> messageListLiveData = new SingleLiveEvent<>();

    @Inject
    public GetMessageListViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getMessageList() {
        messageListLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getMessageListUseCase.execute(new Observer<List<Message>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Message> messages) {
                List<MessageViewData> list = Observable.fromIterable(messages)
                        .map(message -> mapper.mapToViewData(message))
                        .toList()
                        .blockingGet();
                messageListLiveData.postValue(new Resource(ResourceState.SUCCESS, list, null));
            }

            @Override
            public void onError(Throwable e) {
                messageListLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, null);

        return messageListLiveData;
    }


    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
