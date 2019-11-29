package com.vit.mychat.data.secret_message

import com.vit.mychat.data.secret_message.source.SecretCache
import com.vit.mychat.data.secret_message.source.SecretRemote
import com.vit.mychat.domain.usecase.auth.repository.AuthRepository
import com.vit.mychat.domain.usecase.secret.repository.SecretRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecretRepositoryImpl @Inject constructor() : SecretRepository {

    @Inject
    lateinit var secretCache: SecretCache

    @Inject
    lateinit var secretRemote: SecretRemote

    @Inject
    lateinit var authRepository: AuthRepository

    override fun getPublicKey(uid: String): Single<String> {
        secretCache.getPublicKey(uid).let {
            if(it.isNotEmpty()) return Single.defer { Single.just(it) }
        }

        return secretRemote.getPublicKey(uid)
                .doOnSuccess {
                    println("secretRemote.getPublicKey doOnSuccess")
                    secretCache.savePublicKey(uid, it)
                }

    }

    override fun savePublicKey(uid: String, publicKey: String): Completable {
        secretCache.savePublicKey(uid, publicKey)

        return secretRemote.savePublicKey(uid, publicKey)
    }

    override fun getCurrentUserPrivateKey(): String {
        return secretCache.getCurrentUserPrivateKey()
    }

    override fun getCurrentUserPublicKey(): String {
        return secretCache.getCurrentUserPublicKey()
    }

    override fun saveCurrentUserPublicKey(publicKey: String): Completable {
        return savePublicKey(authRepository.currentUserId, publicKey)
    }

    override fun saveCurrentUserPrivateKey(privateKey: String) {
        return secretCache.saveCurrentUserPrivateKey(privateKey)
    }
}