package com.easyhz.patchnote.data.repository.sign

import com.easyhz.patchnote.data.datasource.remote.auth.AuthDataSource
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : SignRepository {
    override suspend fun signInWithPhone(phoneNumber: String): Result<Unit> {
        return authDataSource.signInWithPhone(phoneNumber)
    }

    override suspend fun verifyOTP(
        phoneNumber: String,
        otp: String
    ): Result<Unit> {
        return authDataSource.verifyOTP(phoneNumber, otp)
    }
}