package com.vit.mychat.data.group.source;

import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.data.user.model.UserEntity;

import java.util.List;

import io.reactivex.Single;

public interface GroupRemote {

    Single<GroupEntity> createGroup(List<UserEntity> userList);
}
