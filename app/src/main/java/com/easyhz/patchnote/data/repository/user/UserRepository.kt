package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.core.model.user.User

interface UserRepository {
    fun isLogin(): Boolean
    fun getUserId(): String?
    fun logOut()
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun getUserFromLocal(): Result<User>
    suspend fun updateUser(user: User): Unit
    suspend fun getUserFromRemote(uid: String): Result<User>
    suspend fun deleteUserFromLocal(): Unit
    suspend fun deleteUserFromRemote(uid: String): Result<Unit>
    suspend fun updateUserFromRemote(): Result<Unit>
    suspend fun isFirstOpen(): Result<Boolean>
    suspend fun setIsFirstOpen(isFirstOpen: Boolean): Unit
}