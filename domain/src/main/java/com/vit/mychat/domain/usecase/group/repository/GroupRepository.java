package com.vit.mychat.domain.usecase.group.repository;

import com.vit.mychat.domain.usecase.group.model.Group;

import io.reactivex.Single;

public interface GroupRepository {

    Single<Group> createGroup(Group group);
}
