package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.core.model.user.User

interface UserRepository {
    fun isLogin(): Boolean
    fun getUserId(): String?
    fun logout()
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun getUserFromLocal(): Result<User>
    suspend fun updateUser(user: User): Unit
}