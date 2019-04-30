package com.vit.mychat.data.group;

import com.vit.mychat.data.group.mapper.GroupEntityMapper;
import com.vit.mychat.data.group.source.GroupRemote;
import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.domain.usecase.group.repository.GroupRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GroupRepositoryImpl implements GroupRepository {

    @Inject
    GroupRemote groupRemote;

    @Inject
    GroupEntityMapper mapper;

    @Inject
    public GroupRepositoryImpl() {
    }

    @Override
    public Single<Group> createGroup(Group group) {
        return groupRemote.createGroup(mapper.mapToEntity(group))
                .map(mapper::mapFromEntity);
    }
}
