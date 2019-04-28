package com.vit.mychat.presentation.feature.chat;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.chat.GetChatListUseCase;
import com.vit.mychat.domain.usecase.chat.model.Chat;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.chat.mapper.ChatViewDataMapper;
import com.vit.mychat.presentation.feature.chat.model.ChatViewData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetChatListViewModel extends ViewModel {

    @Inject
    GetChatListUseCase getChatListUseCase;

    @Inject
    ChatViewDataMapper mapper;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> chatListLiveData = new SingleLiveEvent<>();

    @Inject
    public GetChatListViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getChatList() {
        chatListLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getChatListUseCase.execute(new Observer<List<Chat>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Chat> chats) {
                List<ChatViewData> list = Observable.fromIterable(chats)
                        .map(user -> mapper.mapToViewData(user))
                        .toList()
                        .blockingGet();
                chatListLiveData.postValue(new Resource(ResourceState.SUCCESS, list, null));
            }

            @Override
            public void onError(Throwable e) {
                chatListLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, null);

        return chatListLiveData;
    }


    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
