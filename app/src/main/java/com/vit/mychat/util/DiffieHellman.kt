package com.vit.mychat.util

import android.util.Base64
import android.util.Log
import com.vit.mychat.cache.common.getString
import com.vit.mychat.cache.common.toPrivateKey
import com.vit.mychat.cache.common.toPublicKey
import com.vit.mychat.data.secret_message.source.SecretMessageCache
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.exitProcess

@Singleton
class DiffieHellman @Inject constructor() {

    @Inject
    lateinit var secretMessageCache: SecretMessageCache

    val keyAgreement: KeyAgreement = KeyAgreement.getInstance("ECDH")

    var sharedSecret: ByteArray? = null

    fun setReceiverPublicKey(publicKeyString: String) {
        initKeyAgreement()

        keyAgreement.doPhase(publicKeyString.toPublicKey(), true)
        sharedSecret = keyAgreement.generateSecret()
    }

    private fun initKeyAgreement() {
        if (secretMessageCache.getCurrentUserPrivateKey().isEmpty()) {
            val pair = generatePairKey()
            secretMessageCache.saveCurrentUserPrivateKey(pair.private.getString())
            secretMessageCache.saveCurrentUserPublicKey(pair.public.getString())
            keyAgreement.init(pair.private)
            Log.i("DiffieHellman", "initKeyAgreement: ${pair.public.getString()}")
            Log.i("DiffieHellman", "initKeyAgreement: ${secretMessageCache.getCurrentUserPublicKey()}")

            Log.i("DiffieHellman", "initKeyAgreement: ${pair.private.getString() == secretMessageCache.getCurrentUserPrivateKey()}")

        } else {
            keyAgreement.init(secretMessageCache.getCurrentUserPrivateKey().toPrivateKey())
        }
    }

    fun encrypt(msg: String): String {
        try {
            val key = generateKey()
            val c = Cipher.getInstance(ALGO)
            c.init(Cipher.ENCRYPT_MODE, key)
            val encVal = c.doFinal(msg.toByteArray())
            return Base64.encodeToString(encVal, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return msg
    }

    fun decrypt(encryptedData: String): String {
        try {
            val key = generateKey()
            val c = Cipher.getInstance(ALGO)
            c.init(Cipher.DECRYPT_MODE, key)
            var decodedValue: ByteArray? = null
            try {
                decodedValue = Base64.decode(encryptedData, Base64.DEFAULT)
            } catch (e: IllegalArgumentException) {
                println("invalid data received. channel may be compromised. exiting.")
                exitProcess(0)
            }

            val decValue = c.doFinal(decodedValue!!)
            return String(decValue)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return encryptedData
    }

    private fun generateKey(): Key {
        return SecretKeySpec(sharedSecret, ALGO)
    }

    private fun generatePairKey(): KeyPair{
        val kpg = KeyPairGenerator.getInstance("EC")
        kpg.initialize(256)
        return kpg.generateKeyPair()
    }

    companion object {
        const val  ALGO = "AES"

    }
}