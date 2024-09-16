package com.easyhz.patchnote.data.datasource.auth

import android.app.Activity
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

interface AuthDataSource {
    fun isLogin(): Boolean
    fun getUserId(): String?
    fun logout()
    fun verifyPhoneNumber(phoneNumber: String, activity: Activity, callbacks: OnVerificationStateChangedCallbacks): Result<Unit>
    suspend fun getCredentials(verificationId: String, code: String): Result<PhoneAuthCredential>
    suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<AuthResult>
    suspend fun saveUser(saveUserRequest: SaveUserRequest): Result<Unit>
}