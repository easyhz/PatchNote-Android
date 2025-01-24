package com.easyhz.patchnote.data.datasource.remote.auth

import android.app.Activity
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.model.sign.response.UserResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

interface AuthDataSource {
    fun isLogin(): Boolean
    fun getUserId(): String?
    fun logOut()
    fun verifyPhoneNumber(phoneNumber: String, activity: Activity, callbacks: OnVerificationStateChangedCallbacks): Result<Unit>
    suspend fun getCredentials(verificationId: String, code: String): Result<PhoneAuthCredential>
    suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<AuthResult>
    suspend fun saveUser(saveUserRequest: SaveUserRequest): Result<Unit>
    suspend fun getUser(uid: String): Result<UserResponse>
    suspend fun deleteUser(uid: String): Result<Unit>
    suspend fun deleteTeamId(uid: String): Result<Unit>
}