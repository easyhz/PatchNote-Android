package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.data.datasource.auth.AuthDataSource
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): UserRepository {
    override fun isLogin(): Boolean {
        return authDataSource.isLogin()
    }

    override fun getUserId(): String? {
        return authDataSource.getUserId()
    }

    override fun logout() {
        authDataSource.logout()
    }

    override suspend fun saveUser(saveUserRequest: SaveUserRequest): Result<Unit> {
        return authDataSource.saveUser(saveUserRequest)
    }
}