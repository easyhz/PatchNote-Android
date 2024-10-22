package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.datasource.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.user.UserLocalDataSource
import com.easyhz.patchnote.data.mapper.sign.toModel
import com.easyhz.patchnote.data.mapper.sign.toRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userLocalDataSource: UserLocalDataSource
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

    override suspend fun saveUser(user: User): Result<Unit> {
        return authDataSource.saveUser(user.toRequest())
    }

    override suspend fun getUserFromLocal(): Result<User> {
        return userLocalDataSource.getUser()
    }

    override suspend fun updateUser(user: User) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun getUserFromRemote(uid: String): Result<User> {
        return authDataSource.getUser(uid).map { it.toModel() }
    }
}