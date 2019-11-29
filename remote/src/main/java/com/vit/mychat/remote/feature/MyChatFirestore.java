package com.vit.mychat.remote.feature;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.vit.mychat.remote.feature.chat.model.ChatModel;
import com.vit.mychat.remote.feature.group.model.GroupModel;
import com.vit.mychat.remote.feature.message.model.MessageModel;
import com.vit.mychat.remote.feature.user.model.UserModel;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MyChatFirestore {

    Observable<DataSnapshot> getDataSnapshot(DatabaseReference ref);

    /**
     * user
     */
    Observable<UserModel> getUserById(String userId);

    Single<UserModel> getUserByIdSingle(String userId);

    Completable updateUser(UserModel userModel);

    Observable<String> getRelationship(String fromId, String toId);

    Completable updateUserRelationship(String fromId, String toId, String type);

    Observable<List<UserModel>> getUserList();

    Observable<List<UserModel>> getFriendList(String userId, String type);

    void setOnline(boolean isOnline);

    /**
     * auth
     */
    Single<String> login(String email, String password);

    Single<String> register(String email, String password);

    void signOut();

    /**
     * message
     */
    Observable<List<MessageModel>> getMessageList(String userId);

    Completable sendMessage(String userId, String message, String type);

    Completable sendSecretMessage(String userId, String message, String type);

    Observable<List<MessageModel>> getSecretMessageList(String userId);

    /**
     * chat
     */
    Observable<List<ChatModel>> getChatList();

    Observable<List<ChatModel>> getSecretChatList();

    /**
     * news
     */
    Observable<List<String>> getNewsList();

    /**
     * group
     */
    Single<GroupModel> createGroup(List<UserModel> userModelList);


    /**
     * image
     */
    Single<String> updateImage(File image, String type);

    /**
     * secret
     */
    Completable savePublicKey(String uid, String publicKey);

    Single<String> getPublicKey(String uid);


}
