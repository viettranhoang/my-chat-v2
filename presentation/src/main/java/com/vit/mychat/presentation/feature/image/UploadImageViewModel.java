package com.vit.mychat.presentation.feature.image;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.image.UploadImageUseCase;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.image.config.ImageTypeConfig;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UploadImageViewModel extends ViewModel {

    @Inject
    UploadImageUseCase uploadImageUseCase;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> imageLiveData = new SingleLiveEvent<>();

    @Inject
    public UploadImageViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> uploadImage(File image, @ImageTypeConfig String type) {
        imageLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        uploadImageUseCase.execute(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(String url) {
                imageLiveData.postValue(new Resource(ResourceState.SUCCESS, url, null));
            }

            @Override
            public void onError(Throwable e) {
                imageLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }
        }, UploadImageUseCase.Params.forUpdateImage(image, type));

        return imageLiveData;
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
