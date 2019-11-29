package com.vit.mychat.data.secret_message.source

import io.reactivex.Completable
import io.reactivex.Single

interface SecretRemote {

    fun savePublicKey(uid: String, publicKey: String): Completable

    fun getPublicKey(uid: String): Single<String>
}
