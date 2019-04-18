package com.vit.mychat.remote.feature;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vit.mychat.remote.common.Constants;
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
public class MyChatFirestoreFactory implements MyChatFirestore{

    private FirebaseFirestore database;
    private FirebaseAuth auth;

    @Inject
    public MyChatFirestoreFactory() {
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
                        if (documentSnapshot != null && documentSnapshot.exists())
                            emitter.onNext(documentSnapshot.toObject(UserModel.class));
                        else
                            emitter.onError(new Throwable("Không có dữ liệu"));
                    });
            emitter.setCancellable(() -> listenerRegistration.remove());
        });
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
        Map<String, String> data = new HashMap<>();
        data.put(toId, "");
        return Observable.create(emitter -> {
            ListenerRegistration listenerRegistration = database.collection(Constants.TABLE_FRIEND)
                    .document(fromId)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null)
                            emitter.onError(e);
                        if (documentSnapshot != null && documentSnapshot.exists())
                            emitter.onNext(documentSnapshot.getString(toId));
                        else {
                            database.collection(Constants.TABLE_FRIEND).document(fromId).set(data);
                            emitter.onNext("");
                        }
                    });

            emitter.setCancellable(() -> listenerRegistration.remove());
        });
    }

    @Override
    public Completable updateUserRelationship(String fromId, String toId, String type) {
        return Completable.create(emitter -> database.collection(Constants.TABLE_FRIEND)
                .document(fromId)
                .update(toId, type)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        emitter.onComplete();
                    else
                        emitter.onError(task.getException());
                }));
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
}
