package com.vit.mychat.util

import android.util.Base64
import android.util.Log
import com.vit.mychat.cache.common.getString
import com.vit.mychat.cache.common.toPrivateKey
import com.vit.mychat.cache.common.toPublicKey
import com.vit.mychat.domain.usecase.secret.repository.SecretRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
    lateinit var secretRepository: SecretRepository

    val keyAgreement: KeyAgreement = KeyAgreement.getInstance("ECDH")

    var sharedSecret: ByteArray? = null

    val compositeDisposable = CompositeDisposable()

    fun setReceiverPublicKey(publicKeyString: String) {
        keyAgreement.doPhase(publicKeyString.toPublicKey(), true)
        sharedSecret = keyAgreement.generateSecret()
    }

    fun init(receiverUserId: String) {

        if (secretRepository.getCurrentUserPrivateKey().isEmpty()) {
            val pair = generatePairKey()
            secretRepository.saveCurrentUserPrivateKey(pair.private.getString())
            compositeDisposable.add(secretRepository.saveCurrentUserPublicKey(pair.public.getString()).subscribe())
            keyAgreement.init(pair.private)
        } else {
            keyAgreement.init(secretRepository.getCurrentUserPrivateKey().toPrivateKey())
        }

        secretRepository.getPublicKey(receiverUserId)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<String> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onSuccess(s: String) {
                        setReceiverPublicKey(s)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("DiffieHellman", "onError: get public key", e)
                    }
                })
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

    fun dispose() {
        compositeDisposable.dispose()
    }

    companion object {
        const val  ALGO = "AES"

    }
}