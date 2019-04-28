package com.vit.mychat.remote.feature;

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
import com.vit.mychat.remote.common.Constants;
import com.vit.mychat.remote.common.RxFirebase;
import com.vit.mychat.remote.feature.chat.model.ChatModel;
import com.vit.mychat.remote.feature.group.model.GroupModel;
import com.vit.mychat.remote.feature.message.model.MessageModel;
import com.vit.mychat.remote.feature.user.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.vit.mychat.remote.common.Constants.GROUP_ID;
import static com.vit.mychat.remote.common.Constants.TABLE_FRIEND;
import static com.vit.mychat.remote.common.Constants.TABLE_GROUPS;
import static com.vit.mychat.remote.common.Constants.TABLE_MESSAGE;
import static com.vit.mychat.remote.common.Constants.TABLE_USER;

@Singleton
public class MyChatFirestoreFactory implements MyChatFirestore {
    public static final String TAG = MyChatFirestoreFactory.class.getSimpleName();

    private FirebaseFirestore firebaseFirestore;
    private DatabaseReference userDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference messageDatabase;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String currentUserId;

    @Inject
    public MyChatFirestoreFactory() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE);
        userDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_USER);
        friendDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_FRIEND);
        messageDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE).child(Constants.TABLE_MESSAGE);
        currentUserId = auth.getUid();
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
        map.put("/" + fromId + "/" + toId, type);
        map.put("/" + toId + "/" + fromId, type);

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

                        for (DataSnapshot data : dataSnapshot.child(TABLE_FRIEND).child(currentUserId).getChildren()) {

                            if (data.getValue(String.class).contains(type)) {
                                String idFriend = data.getKey();

                                Log.i(TAG, "idFriend" + idFriend);

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
        auth.signOut();
    }

    /**
     * message
     */
    @Override
    public Observable<List<MessageModel>> getMessageList(String userId) {
        return RxFirebase.getList(messageDatabase.child(currentUserId).child(userId), MessageModel.class);
    }

    @Override
    public Completable sendMessage(String userId, String message) {
        MessageModel messageModel = new MessageModel(message, currentUserId, true, System.currentTimeMillis(), "text");
        String key = messageDatabase.child(currentUserId).child(userId).push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put(String.format(Constants.CHILDREN, currentUserId, userId, key), messageModel.toMap());
        map.put(String.format(Constants.CHILDREN, userId, currentUserId, key), messageModel.toMap());

        return RxFirebase.updateChildren(messageDatabase, map);
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

                        for (DataSnapshot data : dataSnapshot.child(TABLE_MESSAGE).child(currentUserId).getChildren()) {

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
                                chatModel.setGroup(dataSnapshot.child(TABLE_GROUPS).child(idSender).getValue(GroupModel.class));
                            } else {
                                chatModel.setUser(dataSnapshot.child(TABLE_USER).child(idSender).getValue(UserModel.class));
                            }

                            listChat.add(chatModel);
                        }
                        emitter.onNext(listChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }
}
