package com.easyhz.patchnote.data.datasource.user

import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest

interface UserLocalDataSource {
    suspend fun updateUser(user: SaveUserRequest): Unit
    suspend fun getUser(): Result<SaveUserRequest>
}