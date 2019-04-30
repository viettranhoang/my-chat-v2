package com.vit.mychat.data.group.source;

import com.vit.mychat.data.group.model.GroupEntity;

import io.reactivex.Single;

public interface GroupRemote {

    Single<GroupEntity> createGroup(GroupEntity group);
}
