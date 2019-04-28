package com.vit.mychat.data.group.mapper;

import com.vit.mychat.data.Mapper;
import com.vit.mychat.data.group.model.GroupEntity;
import com.vit.mychat.domain.usecase.group.model.Group;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GroupEntityMapper implements Mapper<GroupEntity, Group> {

    @Inject
    public GroupEntityMapper() {
    }

    @Override
    public GroupEntity mapToEntity(Group type) {
        if (type == null) {
            return null;
        }
        return new GroupEntity(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }

    @Override
    public Group mapFromEntity(GroupEntity type) {
        if (type == null) {
            return null;
        }
        return new Group(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }
}
