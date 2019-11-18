package com.vit.mychat.cache.common

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

object Utils {

    @JvmStatic
    fun getStringFromPublicKey(publicKey: PublicKey): String {
        return Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)
    }

    @JvmStatic
    fun getPublicKey(publicKey: String): PublicKey {
        val byte = Base64.decode(publicKey, Base64.DEFAULT)
        return KeyFactory.getInstance("ECDH").generatePublic(X509EncodedKeySpec(byte))
    }
}