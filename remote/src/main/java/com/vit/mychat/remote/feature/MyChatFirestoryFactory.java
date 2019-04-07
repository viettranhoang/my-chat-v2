package com.vit.mychat.remote.feature;

import com.google.firebase.firestore.FirebaseFirestore;
import com.vit.mychat.remote.common.Constants;
import com.vit.mychat.remote.feature.user.model.UserModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MyChatFirestoryFactory implements MyChatFirestore{

    private FirebaseFirestore database;

    @Inject
    public MyChatFirestoryFactory() {
        database = FirebaseFirestore.getInstance();
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
}
