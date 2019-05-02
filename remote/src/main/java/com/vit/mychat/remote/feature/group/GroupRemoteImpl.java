package com.vit.mychat.remote.feature.group;

import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.data.group.source.GroupRemote;
import com.vit.mychat.data.user.model.UserEntity;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.group.mapper.GroupModelMapper;
import com.vit.mychat.remote.feature.user.mapper.UserModelMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class GroupRemoteImpl implements GroupRemote {

    @Inject
    GroupModelMapper mapperGroup;

    @Inject
    UserModelMapper mapperUser;

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public GroupRemoteImpl() {
    }

    @Override
    public Single<GroupEntity> createGroup(List<UserEntity> userList) {
        return myChatFirestore.createGroup(Observable.fromIterable(userList)
                                                .map(mapperUser::mapToModel)
                                                .toList()
                                                .blockingGet())
                .map(mapperGroup::mapToEntity);
    }
}
