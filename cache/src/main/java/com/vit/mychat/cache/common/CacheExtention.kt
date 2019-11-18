package com.vit.mychat.cache.common

import android.util.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

fun PublicKey.getString(): String {
    return Base64.encodeToString(encoded, Base64.DEFAULT)
}

fun String.toPublicKey(): PublicKey {
    val byte = Base64.decode(this, Base64.DEFAULT)
    return KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(byte))
}

fun PrivateKey.getString(): String {
    return Base64.encodeToString(encoded, Base64.DEFAULT)
}

fun String.toPrivateKey(): PrivateKey {
    val privateKeyStringBytes = Base64.decode(this, Base64.DEFAULT)
    val keySpec = PKCS8EncodedKeySpec(privateKeyStringBytes)
    return KeyFactory.getInstance("EC").generatePrivate(keySpec)
}