package com.vit.mychat.remote.feature;

import com.vit.mychat.remote.feature.user.model.UserModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MyChatFirestore {

    /**
     * user
     */
    Observable<UserModel> getUserById(String userId);

    Single<UserModel> getUserByIdSingle(String userId);

    Completable updateUser(UserModel userModel);

    Observable<String> getRelationship(String fromId, String toId);

    Completable updateUserRelationship(String fromId, String toId, String type);

    Observable<List<UserModel>> getUserList();

    Single<List<String>> getIdFriendList(String userId, String type);

    /**
     * auth
     */
    Single<String> login(String email, String password);

    Single<String> register(String email, String password);

    void signOut();
}
