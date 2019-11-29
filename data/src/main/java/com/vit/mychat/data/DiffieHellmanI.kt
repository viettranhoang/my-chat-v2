package com.vit.mychat.data

interface DiffieHellmanI {

    fun init()

    fun encrypt(msg: String): String

    fun decrypt(encryptedData: String): String

    fun setReceiverPublicKey(publicKeyString: String)
}