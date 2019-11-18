package com.vit.mychat.data.secret_message.source

interface SecretMessageCache {

    fun savePublicKey(uid: String, publicKey: String)

    fun getPublicKey(uid: String): String

    fun getCurrentUserPrivateKey(): String

    fun getCurrentUserPublicKey(): String

    fun saveCurrentUserPublicKey(publicKey: String)

    fun saveCurrentUserPrivateKey(privateKey: String)
}
