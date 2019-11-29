package com.vit.mychat.remote.feature.secret

import com.vit.mychat.data.secret_message.source.SecretRemote
import com.vit.mychat.remote.feature.MyChatFirestore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecretRemoteImpl @Inject constructor(): SecretRemote{

    @Inject
    lateinit var myChatFirestore: MyChatFirestore

    override fun savePublicKey(uid: String, publicKey: String): Completable {
        return myChatFirestore.savePublicKey(uid, publicKey)
    }

    override fun getPublicKey(uid: String): Single<String> {
        return myChatFirestore.getPublicKey(uid)
    }
}