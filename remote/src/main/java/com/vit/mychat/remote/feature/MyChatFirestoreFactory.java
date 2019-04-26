package com.vit.mychat.remote.feature;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
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
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth auth;
    private String currentUserId;

    @Inject
    public MyChatFirestoreFactory() {
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATABASE);
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
        return Observable.create(emitter -> {
            ListenerRegistration listenerRegistration = database.collection(Constants.TABLE_USER).document(userId)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null)
                            emitter.onError(e);
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Log.i(TAG, "getUserById: " + documentSnapshot.toObject(UserModel.class).getName());
                            emitter.onNext(documentSnapshot.toObject(UserModel.class));
                        } else
                            emitter.onError(new Throwable("Không có dữ liệu"));
                    });
            emitter.setCancellable(() -> listenerRegistration.remove());
        });
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
        return Completable.create(emitter -> database.collection(Constants.TABLE_USER)
                .document(userModel.getId())
                .set(userModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        emitter.onComplete();
                    else
                        emitter.onError(task.getException());
                }));
    }

    @Override
    public Observable<String> getRelationship(String fromId, String toId) {
        Map<String, String> dataToId = new HashMap<>();
        Map<String, String> dataFromId = new HashMap<>();
        dataToId.put(toId, "");
        dataFromId.put(fromId, "");
        return Observable.create(emitter -> {
            ListenerRegistration listenerRegistration = database.collection(Constants.TABLE_FRIEND)
                    .document(fromId)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null)
                            emitter.onError(e);
                        if (documentSnapshot != null && documentSnapshot.exists())
                            emitter.onNext(documentSnapshot.getString(toId));
                        else {
                            database.collection(Constants.TABLE_FRIEND).document(fromId).set(dataToId);
                            database.collection(Constants.TABLE_FRIEND).document(toId).set(dataFromId);
                            emitter.onNext("");
                        }
                    });

            emitter.setCancellable(() -> listenerRegistration.remove());
        });
    }

    @Override
    public Completable updateUserRelationship(String fromId, String toId, String type) {
        return Completable.create(emitter -> {
            WriteBatch batch = database.batch();
            batch.update(database.collection(Constants.TABLE_FRIEND).document(fromId), toId, type);
            batch.update(database.collection(Constants.TABLE_FRIEND).document(toId), fromId, type);

            batch.commit()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            emitter.onComplete();
                        else
                            emitter.onError(task.getException());
                    });
        });
    }

    @Override
    public Observable<List<UserModel>> getUserList() {
        return Observable.create(emitter -> {
            ListenerRegistration listenerRegistration = database.collection(Constants.TABLE_USER)
                    .addSnapshotListener((value, e) -> {
                        if (e != null)
                            emitter.onError(e);

                        List<UserModel> userModelList = new ArrayList<>();
                        for (QueryDocumentSnapshot user : value) {
                            if (user != null) {
                                userModelList.add(user.toObject(UserModel.class));
                            }
                        }
                        emitter.onNext(userModelList);
                    });
            emitter.setCancellable(() -> listenerRegistration.remove());
        });
    }

    @Override
    public Single<List<String>> getIdFriendList(String userId, String type) {
        return Single.create(emitter -> database
                .collection(Constants.TABLE_FRIEND)
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> idList = new ArrayList<>();
                            Map<String, Object> map = document.getData();
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                if (entry.getValue().toString().contains(type)) {
                                    idList.add(entry.getKey());
                                }
                            }
                            emitter.onSuccess(idList);
                        } else
                            emitter.onError(new Throwable("Không có dữ liệu"));
                    } else
                        emitter.onError(task.getException());
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
    public Observable<List<MessageModel>> getMessageList() {
        return RxFirebase.getList(firebaseDatabase
                .child(Constants.TABLE_MESSAGE)
                .child(currentUserId), MessageModel.class);
    }

    @Override
    public Completable sendMessage(String userId, String message) {
        return null;
    }
}
