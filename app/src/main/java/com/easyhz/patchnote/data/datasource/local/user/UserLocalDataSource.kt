package com.easyhz.patchnote.data.datasource.local.user

import com.easyhz.patchnote.core.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun updateUser(user: User): Unit
    suspend fun getUser(): Result<User>
    suspend fun deleteUser(): Unit
    suspend fun isFirstOpen(): Result<Boolean>
    suspend fun setIsFirstOpen(newValue: Boolean): Unit
    suspend fun updateTeamName(teamName: String): Unit
    suspend fun getTeamName(): Flow<String>
    suspend fun deleteTeamName()
    suspend fun isOfflineFirstOpen(): Result<Boolean>
    suspend fun setIsOfflineFirstOpen(newValue: Boolean)

    suspend fun setCurrentTeamId(newValue: String)
    suspend fun getCurrentTeamId(): Result<String?>
}