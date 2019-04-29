package com.vit.mychat.presentation.feature.news;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.news.GetNewsListUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GetNewsListViewModel extends ViewModel {

    @Inject
    GetNewsListUseCase getNewsListUseCase;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> newsListLiveData = new SingleLiveEvent<>();

    @Inject
    public GetNewsListViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> getNewsList() {
        newsListLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        getNewsListUseCase.execute(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<String> newss) {
                newsListLiveData.postValue(new Resource(ResourceState.SUCCESS, newss, null));
            }

            @Override
            public void onError(Throwable e) {
                newsListLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }

            @Override
            public void onComplete() {

            }
        }, null);

        return newsListLiveData;
    }


    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
