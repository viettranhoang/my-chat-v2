package com.vit.mychat.presentation.feature.group.mapper;


import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.presentation.Mapper;
import com.vit.mychat.presentation.feature.group.model.GroupViewData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GroupViewDataMapper implements Mapper<Group, GroupViewData> {

    @Inject
    public GroupViewDataMapper() {
    }


    @Override
    public GroupViewData mapToViewData(Group type) {
        if (type == null) {
            return null;
        }
        return new GroupViewData(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }

    @Override
    public Group mapFromViewData(GroupViewData type) {
        if (type == null) {
            return null;
        }
        return new Group(type.getId(), type.getName(), type.getAvatar(), type.getMembers());
    }
}
