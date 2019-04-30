package com.vit.mychat.remote.feature.group;

import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.data.group.source.GroupRemote;
import com.vit.mychat.remote.feature.MyChatFirestore;
import com.vit.mychat.remote.feature.group.mapper.GroupModelMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GroupRemoteImpl implements GroupRemote {

    @Inject
    GroupModelMapper mapper;

    @Inject
    MyChatFirestore myChatFirestore;

    @Inject
    public GroupRemoteImpl() {
    }

    @Override
    public Single<GroupEntity> createGroup(GroupEntity group) {
        return myChatFirestore.createGroup(mapper.mapToModel(group))
                .map(mapper::mapToEntity);
    }
}
