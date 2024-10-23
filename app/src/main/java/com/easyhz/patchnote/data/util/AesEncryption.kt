package com.easyhz.patchnote.data.util

import android.util.Base64
import android.util.Log
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
const val GCM_IV_LENGTH = 12
const val GCM_TAG_LENGTH = 128

class AesEncryption(private val key: String) {
    private val tag = "AesEncryption::class"
    init {
        require(key.length == 16) { "AES 키는 16자리여야 합니다." }
    }

    fun encrypt(data: String): String {
        return try {
            val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val iv = ByteArray(GCM_IV_LENGTH)
            SecureRandom().nextBytes(iv)
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)
            val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            val encryptedWithIv = iv + encryptedData

            Base64.encodeToString(encryptedWithIv, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e(tag, "Encryption error: ${e.message}")
            throw e
        }
    }

    fun decrypt(encryptedData: String?): String {
        return try {
            if (encryptedData == null) throw IllegalArgumentException("데이터가 없습니다.")
            val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
            val iv = decodedData.copyOfRange(0, GCM_IV_LENGTH)
            val encryptedBytes = decodedData.copyOfRange(GCM_IV_LENGTH, decodedData.size)
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            val decryptedData = cipher.doFinal(encryptedBytes)
            String(decryptedData, Charsets.UTF_8)
        } catch (e: Exception) {
            Log.e(tag, "Decryption error: ${e.message}")
            throw e
        }
    }
}