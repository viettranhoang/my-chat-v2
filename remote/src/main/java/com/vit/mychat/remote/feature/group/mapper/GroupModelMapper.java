package com.vit.mychat.remote.feature.group.mapper;


import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.remote.common.Mapper;
import com.vit.mychat.remote.feature.group.model.GroupModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GroupModelMapper implements Mapper<GroupModel, GroupEntity> {

    @Inject
    public GroupModelMapper() {
    }

    @Override
    public GroupEntity mapToEntity(GroupModel type) {
        if (type == null) {
            return null;
        }
        return new GroupEntity(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }

    @Override
    public GroupModel mapToModel(GroupEntity type) {
        if (type == null) {
            return null;
        }
        return new GroupModel(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }
}
