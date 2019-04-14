package com.vit.mychat.remote.feature;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vit.mychat.remote.common.Constants;
import com.vit.mychat.remote.feature.user.model.UserModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
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

    @Override
    public Single<UserModel> getUserById(String userId) {
        return Single.create(emitter -> {
            database.collection(Constants.TABLE_USER).document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot ->
                            emitter.onSuccess(documentSnapshot.toObject(UserModel.class)))
                    .addOnFailureListener(e -> emitter.onError(e));
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
