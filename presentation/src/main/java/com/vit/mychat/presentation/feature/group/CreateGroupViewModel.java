package com.vit.mychat.presentation.feature.group;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vit.mychat.domain.usecase.group.CreateGroupUseCase;
import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.presentation.SingleLiveEvent;
import com.vit.mychat.presentation.data.Resource;
import com.vit.mychat.presentation.data.ResourceState;
import com.vit.mychat.presentation.feature.group.mapper.GroupViewDataMapper;
import com.vit.mychat.presentation.feature.user.mapper.UserViewDataMapper;
import com.vit.mychat.presentation.feature.user.model.UserViewData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CreateGroupViewModel extends ViewModel {

    @Inject
    CreateGroupUseCase createGroupUseCase;

    @Inject
    UserViewDataMapper mapperUser;

    @Inject
    GroupViewDataMapper mapperGroup;

    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent<Resource> createGroupLiveData = new SingleLiveEvent<>();

    @Inject
    public CreateGroupViewModel() {
        compositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<Resource> createGroup(List<UserViewData> userList) {
        createGroupLiveData.postValue(new Resource(ResourceState.LOADING, null, null));

        createGroupUseCase.execute(new SingleObserver<Group>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Group group) {
                createGroupLiveData.postValue(new Resource(ResourceState.SUCCESS, mapperGroup.mapToViewData(group), null));
            }

            @Override
            public void onError(Throwable e) {
                createGroupLiveData.postValue(new Resource(ResourceState.ERROR, null, e));
            }
        }, CreateGroupUseCase.Params.forCreateGroup(
                Observable.fromIterable(userList)
                        .map(mapperUser::mapFromViewData)
                        .toList()
                        .blockingGet()));

        return createGroupLiveData;
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
