package com.vit.mychat.remote.feature;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vit.mychat.remote.common.Constants;
import com.vit.mychat.remote.common.RxFirebase;
import com.vit.mychat.remote.common.Utils;
import com.vit.mychat.remote.feature.chat.model.ChatModel;
import com.vit.mychat.remote.feature.group.model.GroupModel;
import com.vit.mychat.remote.feature.message.model.MessageModel;
import com.vit.mychat.remote.feature.user.config.UserRelationshipConfig;
import com.vit.mychat.remote.feature.user.model.UserModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.vit.mychat.remote.common.Constants.CURRENT_USER_AVATAR;
import static com.vit.mychat.remote.common.Constants.FRIEND_TYPE;
import static com.vit.mychat.remote.common.Constants.GROUP_ID;
import static com.vit.mychat.remote.common.Constants.JPG_IMAGE;
import static com.vit.mychat.remote.common.Constants.ROW_AVATAR;
import static com.vit.mychat.remote.common.Constants.ROW_MEMBERS;
import static com.vit.mychat.remote.common.Constants.ROW_NAME;
import static com.vit.mychat.remote.common.Constants.TABLE_FRIEND;
import static com.vit.mychat.remote.common.Constants.TABLE_GROUP;
import static com.vit.mychat.remote.common.Constants.TABLE_IMAGE;
import static com.vit.mychat.remote.common.Constants.TABLE_MESSAGE;
import static com.vit.mychat.remote.common.Constants.TABLE_PUBLIC_KEY;
import static com.vit.mychat.remote.common.Constants.TABLE_SECRET_MESSSAGE;
import static com.vit.mychat.remote.common.Constants.TABLE_USER;

@Singleton
public class MyChatFirestoreFactory implements MyChatFirestore {
    public static final String TAG = MyChatFirestoreFactory.class.getSimpleName();

    private StorageReference storage;
    private FirebaseFirestore firebaseFirestore;
    private DatabaseReference userDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference messageDatabase;
    private DatabaseReference secretMessageDatabase;
    private DatabaseReference publicKeyDatabase;
    private DatabaseReference groupDatabase;
    private DatabaseReference database;
    private FirebaseAuth auth;

    @Inject
    public MyChatFirestoreFactory() {
        storage = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE);
        userDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_USER);
        friendDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_FRIEND);
        messageDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_MESSAGE);
        secretMessageDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_SECRET_MESSSAGE);
        groupDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(TABLE_GROUP);
        publicKeyDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(TABLE_PUBLIC_KEY);

        userDatabase.keepSynced(true);
        friendDatabase.keepSynced(true);
        messageDatabase.keepSynced(true);
    }


    @Override
    public Observable<DataSnapshot> getDataSnapshot(DatabaseReference ref) {
        return RxFirebase.getDataSnapshot(ref);
    }

    /**
     * user
     */
    @Override
    public Observable<UserModel> getUserById(String userId) {
        return RxFirebase.getValue(userDatabase.child(userId), UserModel.class);
    }

    @Override
    public Single<UserModel> getUserByIdSingle(String userId) {
        return Single.create(emitter -> firebaseFirestore
                .collection(Constants.TABLE_USER)
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            emitter.onSuccess(document.toObject(UserModel.class));
                        } else
                            emitter.onError(new Throwable("Không có dữ liệu"));
                    } else
                        emitter.onError(task.getException());

                }));
    }

    @Override
    public Completable updateUser(UserModel userModel) {
        return RxFirebase.setValue(userDatabase.child(userModel.getId()), userModel);
    }

    @Override
    public Observable<String> getRelationship(String fromId, String toId) {
        Map<String, Object> map = new HashMap<>();
        map.put("/" + fromId + "/" + toId, "");
        map.put("/" + toId + "/" + fromId, "");

        return Observable.create(emitter ->
                friendDatabase.child(fromId).child(toId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(String.class) == null) {
                            friendDatabase.updateChildren(map);
                        } else emitter.onNext(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Completable updateUserRelationship(String fromId, String toId, String type) {
        Map<String, String> map = new HashMap<>();
        if(type.equals(UserRelationshipConfig.SENT)) {
            map.put("/" + fromId + "/" + toId, UserRelationshipConfig.SENT);
            map.put("/" + toId + "/" + fromId, UserRelationshipConfig.RECEIVE);
        } else {
            map.put("/" + fromId + "/" + toId, type);
            map.put("/" + toId + "/" + fromId, type);
        }

        return RxFirebase.updateChildren(friendDatabase, map);
    }

    @Override
    public Observable<List<UserModel>> getUserList() {
        return RxFirebase.getList(userDatabase, UserModel.class);
    }

    @Override
    public Observable<List<UserModel>> getFriendList(String userId, String type) {
        return Observable.create(emitter ->
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<UserModel> listUser = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.child(TABLE_FRIEND).child(auth.getUid()).getChildren()) {

                            if (data.getValue(String.class).contains(type)) {
                                String idFriend = data.getKey();

                                listUser.add(dataSnapshot.child(TABLE_USER).child(idFriend).getValue(UserModel.class));
                            }
                        }
                        emitter.onNext(listUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public void setOnline(boolean isOnline) {
        userDatabase.child(auth.getUid()).child("online").setValue(isOnline ? 1 : System.currentTimeMillis());
    }


    /**
     * auth
     */
    @Override
    public Single<String> login(String email, String password) {
        return Single.create(emitter -> auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        emitter.onSuccess(auth.getUid());
                    else
                        emitter.onError(task.getException());
                }));
    }

    @Override
    public Single<String> register(String email, String password) {
        return Single.create(emitter -> auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        emitter.onSuccess(auth.getUid());
                    else
                        emitter.onError(task.getException());
                }));
    }

    @Override
    public void signOut() {
        setOnline(false);
        auth.signOut();
    }

    /**
     * message
     */
    @Override
    public Observable<List<MessageModel>> getMessageList(String userId) {
        //set last message seen true
        messageDatabase.child(auth.getUid()).child(userId).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    messageDatabase.child(auth.getUid()).child(userId).child(data.getKey()).child("seen").setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return RxFirebase.getList(messageDatabase.child(auth.getUid()).child(userId), MessageModel.class);
    }

    @Override
    public Completable sendMessage(String userId, String message, String type) {
        return Completable.create(emitter -> {
            MessageModel messageModel = new MessageModel(message, auth.getUid(), false, System.currentTimeMillis(), type, CURRENT_USER_AVATAR);
            String key = messageDatabase.child(auth.getUid()).child(userId).push().getKey();

            Map<String, Object> map = new HashMap<>();

            if (!userId.contains(GROUP_ID)) {
                map.put(String.format(Constants.CHILDREN, auth.getUid(), userId, key), messageModel.toMap());
                map.put(String.format(Constants.CHILDREN, userId, auth.getUid(), key), messageModel.toMap());

                messageDatabase.updateChildren(map, (databaseError, databaseReference) -> {
                    if (!emitter.isDisposed()) {
                        if (null != databaseError) {
                            emitter.onError(databaseError.toException());
                        } else {
                            emitter.onComplete();
                        }
                    }
                });
            } else
                groupDatabase.child(userId).child(ROW_MEMBERS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            map.put(String.format(Constants.CHILDREN, data.getKey(), userId, key), messageModel.toMap());
                        }

                        messageDatabase.updateChildren(map, (databaseError, databaseReference) -> {
                            if (!emitter.isDisposed()) {
                                if (null != databaseError) {
                                    emitter.onError(databaseError.toException());
                                } else {
                                    emitter.onComplete();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                });

        });
    }

    @Override
    public Completable sendSecretMessage(String userId, String message, String type) {
        return Completable.create(emitter -> {
                    MessageModel messageModel = new MessageModel(message, auth.getUid(), false, System.currentTimeMillis(), type, CURRENT_USER_AVATAR);
                    String key = secretMessageDatabase.child(auth.getUid()).child(userId).push().getKey();

                    Map<String, Object> map = new HashMap<>();

                    if (!userId.contains(GROUP_ID)) {
                        map.put(String.format(Constants.CHILDREN, auth.getUid(), userId, key), messageModel.toMap());
                        map.put(String.format(Constants.CHILDREN, userId, auth.getUid(), key), messageModel.toMap());

                        secretMessageDatabase.updateChildren(map, (databaseError, databaseReference) -> {
                            if (!emitter.isDisposed()) {
                                if (null != databaseError) {
                                    emitter.onError(databaseError.toException());
                                } else {
                                    emitter.onComplete();
                                }
                            }
                        });
                    }
        });
    }

    @Override
    public Observable<List<MessageModel>> getSecretMessageList(String userId) {
        return RxFirebase.getList(secretMessageDatabase.child(auth.getUid()).child(userId), MessageModel.class);
    }


    /**
     * chat
     */
    @Override
    public Observable<List<ChatModel>> getChatList() {
        return Observable.create(emitter ->
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<ChatModel> listChat = new ArrayList<>();

                        if (auth.getUid() != null) {
                            for (DataSnapshot data : dataSnapshot.child(TABLE_MESSAGE).child(auth.getUid()).getChildren()) {

                                ChatModel chatModel = new ChatModel();

                                String idSender = data.getKey();

                                Log.i(TAG, "onDataChange: idSender" + idSender);

                                List<MessageModel> list = new ArrayList<>();

                                for (DataSnapshot dataMessage : data.getChildren()) {
                                    list.add(dataMessage.getValue(MessageModel.class));
                                }
                                Log.i(TAG, "onDataChange: setLastMessage" + list.get(list.size() - 1).toString());

                                chatModel.setLastMessage(list.get(list.size() - 1));

                                if (idSender.contains(GROUP_ID)) {
                                    chatModel.setGroup(getGroupModel(dataSnapshot, idSender));
                                } else {
                                    chatModel.setUser(dataSnapshot.child(TABLE_USER).child(idSender).getValue(UserModel.class));
                                }

                                listChat.add(chatModel);
                            }
                        }

                        emitter.onNext(listChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Observable<List<ChatModel>> getSecretChatList() {
        return Observable.create(emitter ->
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<ChatModel> listChat = new ArrayList<>();

                        if (auth.getUid() != null) {
                            for (DataSnapshot data : dataSnapshot.child(TABLE_SECRET_MESSSAGE).child(auth.getUid()).getChildren()) {

                                ChatModel chatModel = new ChatModel();

                                String idSender = data.getKey();

                                Log.i(TAG, "onDataChange: idSender" + idSender);

                                chatModel.setUser(dataSnapshot.child(TABLE_USER).child(idSender).getValue(UserModel.class));

                                listChat.add(chatModel);
                            }
                        }

                        emitter.onNext(listChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    private GroupModel getGroupModel(DataSnapshot database, String groupId) {
        List<String> members = new ArrayList<>();
        for (DataSnapshot dataMember : database.child(TABLE_GROUP).child(groupId).child(ROW_MEMBERS).getChildren()) {
            members.add(dataMember.getKey());
        }
        String name = (String) database.child(TABLE_GROUP).child(groupId).child(ROW_NAME).getValue();
        String avatar = (String) database.child(TABLE_GROUP).child(groupId).child(ROW_AVATAR).getValue();

        return new GroupModel(groupId, name, avatar, members);
    }


    /**
     * news
     */
    @Override
    public Observable<List<String>> getNewsList() {
        return Observable.create(emitter ->
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.child(TABLE_FRIEND).child(auth.getUid()).getChildren()) {

                            if (data.getValue(String.class).contains(FRIEND_TYPE)) {
                                String idFriend = data.getKey();

                                UserModel userModel = dataSnapshot.child(TABLE_USER).child(idFriend).getValue(UserModel.class);
                                list.add(userModel.getNews());
                            }
                        }

                        emitter.onNext(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }


    /**
     * group
     */
    @Override
    public Single<GroupModel> createGroup(List<UserModel> userModelList) {
        return Single.create(emitter -> {

            GroupModel groupModel = createGroupModel(userModelList);

            groupDatabase.child(groupModel.getId()).updateChildren(groupModel.toMap(), (databaseError, databaseReference) -> {
                if (!emitter.isDisposed()) {
                    if (null != databaseError) {
                        emitter.onError(databaseError.toException());
                    } else {
                        emitter.onSuccess(groupModel);
                    }
                }
            });

            MessageModel messageModel = new MessageModel("Hi", auth.getUid(), true, System.currentTimeMillis(), "text", CURRENT_USER_AVATAR);
            String key = messageDatabase.child(auth.getUid()).child(groupModel.getId()).push().getKey();
            Map<String, Object> map = new HashMap<>();
            for (String member : groupModel.getMembers()) {
                map.put(String.format(Constants.CHILDREN, member, groupModel.getId(), key), messageModel.toMap());
            }
            messageDatabase.updateChildren(map);

        });
    }

    private GroupModel createGroupModel(List<UserModel> userModelList) {
        String name = "";
        String id = String.format("%s_%s", GROUP_ID, groupDatabase.push().getKey());
        String avatar = "http://gnemart.com/wp-content/uploads/2018/10/gau-bong-quobee-600x600.jpg";
        List<String> members = new ArrayList<>();

        for (UserModel userModel : userModelList) {
            name += String.format("%s, ", userModel.getName());
            members.add(userModel.getId());
        }
        return new GroupModel(id, name.substring(0, name.length() - 2), avatar, members);
    }


    /**
     * image
     */
    @Override
    public Single<String> updateImage(File image, String type) {
        return Single.create(emitter -> {
            StorageReference filePath = storage.child(TABLE_IMAGE).child(auth.getUid()).child(type)
                    .child(Utils.getRandomString() + JPG_IMAGE);

            filePath.putFile(Uri.fromFile(image))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            filePath.getDownloadUrl().addOnSuccessListener(uri -> emitter.onSuccess(uri.toString()));
                        }
                    })
                    .addOnFailureListener(e -> emitter.onError(e));
        });
    }

    /**
     * secret
     */
    @Override
    public Completable savePublicKey(String uid, String publicKey) {
        return RxFirebase.setValue(publicKeyDatabase.child(uid), publicKey);
    }

    @Override
    public Single<String> getPublicKey(String uid) {
        return RxFirebase.getValueSingle(publicKeyDatabase.child(uid), String.class);
    }
}
