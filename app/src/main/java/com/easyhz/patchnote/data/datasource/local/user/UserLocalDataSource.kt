package com.easyhz.patchnote.data.datasource.local.user

import com.easyhz.patchnote.core.model.user.User

interface UserLocalDataSource {
    suspend fun updateUser(user: User): Unit
    suspend fun getUser(): Result<User>
}