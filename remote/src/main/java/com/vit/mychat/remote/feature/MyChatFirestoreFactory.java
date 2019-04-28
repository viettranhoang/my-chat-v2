package com.vit.mychat.remote.feature;

import android.support.annotation.NonNull;

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

@Singleton
public class MyChatFirestoreFactory implements MyChatFirestore {
    public static final String TAG = MyChatFirestoreFactory.class.getSimpleName();

    private FirebaseFirestore database;
    private DatabaseReference userDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference messageDatabase;
    private FirebaseAuth auth;
    private String currentUserId;

    @Inject
    public MyChatFirestoreFactory() {
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
        return Single.create(emitter -> database
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
    public Single<List<String>> getIdFriendList(String userId, String type) {
        return Single.create(emitter -> friendDatabase
                .child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (data.getValue(String.class).equals(type)) {
                                list.add(data.getKey());
                            }
                        }
                        emitter.onSuccess(list);
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
}
