package com.vit.mychat.data.group;

import com.vit.mychat.data.group.mapper.GroupEntityMapper;
import com.vit.mychat.data.group.source.GroupRemote;
import com.vit.mychat.data.user.mapper.UserEntityMapper;
import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.domain.usecase.group.repository.GroupRepository;
import com.vit.mychat.domain.usecase.user.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class GroupRepositoryImpl implements GroupRepository {

    @Inject
    GroupRemote groupRemote;

    @Inject
    UserEntityMapper mapperUser;

    @Inject
    GroupEntityMapper mapperGroup;

    @Inject
    public GroupRepositoryImpl() {
    }

    @Override
    public Single<Group> createGroup(List<User> userList) {
        return groupRemote.createGroup(Observable.fromIterable(userList)
                                            .map(mapperUser::mapToEntity)
                                            .toList()
                                            .blockingGet())
                .map(mapperGroup::mapFromEntity);
    }
}
