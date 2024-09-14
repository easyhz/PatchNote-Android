package com.easyhz.patchnote.data.repository.sign

import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential


interface SignRepository {
    suspend fun requestVerificationCode(param: RequestVerificationCodeParam): Result<RequestVerificationCodeResponse>
    suspend fun getCredentials(verificationId: String, code: String): Result<PhoneAuthCredential>
    suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<AuthResult>
}