package com.easyhz.patchnote.data.repository.sign

import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential


interface SignRepository {
    suspend fun signInWithPhone(phoneNumber: String): Result<Unit>
    suspend fun verifyOTP(phoneNumber: String, otp: String): Result<Unit>
}