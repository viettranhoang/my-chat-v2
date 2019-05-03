package com.vit.mychat.domain.usecase.group.repository;

import com.vit.mychat.domain.usecase.group.model.Group;
import com.vit.mychat.domain.usecase.user.model.User;

import java.util.List;

import io.reactivex.Single;

public interface GroupRepository {

    Single<Group> createGroup(List<User> userList);


}
