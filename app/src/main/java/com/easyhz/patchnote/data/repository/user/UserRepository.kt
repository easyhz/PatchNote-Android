package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest

interface UserRepository {
    fun isLogin(): Boolean
    fun getUserId(): String?
    fun logout()
    suspend fun saveUser(saveUserRequest: SaveUserRequest): Result<Unit>
}