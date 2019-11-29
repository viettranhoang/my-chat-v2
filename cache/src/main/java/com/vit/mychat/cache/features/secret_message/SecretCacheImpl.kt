package com.vit.mychat.cache.features.secret_message


import com.vit.mychat.cache.common.PrefUtils
import com.vit.mychat.data.auth.source.AuthCache
import com.vit.mychat.data.secret_message.source.SecretCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecretCacheImpl @Inject constructor() : SecretCache{

    @Inject
    lateinit var prefUtils: PrefUtils

    @Inject
    lateinit var authCache: AuthCache

    override fun savePublicKey(uid: String, publicKey: String) {
        prefUtils.set(uid, publicKey)
    }

    override fun getPublicKey(uid: String): String {
        return prefUtils.get(uid, "")
    }

    override fun getCurrentUserPrivateKey(): String {
        return prefUtils.get(authCache.currentUserId + PRIVATE_KEY, "")
    }

    override fun saveCurrentUserPrivateKey(privateKey: String) {
        prefUtils.set(authCache.currentUserId + PRIVATE_KEY, privateKey)
    }

    override fun getCurrentUserPublicKey(): String {
        return prefUtils.get(authCache.currentUserId, "")
    }

    override fun saveCurrentUserPublicKey(publicKey: String) {
        prefUtils.set(authCache.currentUserId, publicKey)
    }

    companion object {
        const val PRIVATE_KEY = "PRIVATE_KEY"
    }

}
