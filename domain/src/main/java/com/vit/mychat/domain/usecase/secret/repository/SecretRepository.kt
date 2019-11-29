package com.vit.mychat.domain.usecase.secret.repository

import io.reactivex.Completable
import io.reactivex.Single

interface SecretRepository {

    fun getPublicKey(uid: String): Single<String>

    fun savePublicKey(uid: String, publicKey: String): Completable

    fun getCurrentUserPrivateKey(): String

    fun getCurrentUserPublicKey(): String

    fun saveCurrentUserPublicKey(publicKey: String): Completable

    fun saveCurrentUserPrivateKey(privateKey: String)

}