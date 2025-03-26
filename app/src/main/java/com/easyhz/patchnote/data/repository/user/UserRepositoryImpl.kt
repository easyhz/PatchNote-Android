package com.easyhz.patchnote.data.repository.user

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.datasource.remote.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.local.user.UserLocalDataSource
import com.easyhz.patchnote.data.datasource.remote.team.TeamRemoteDateSource
import com.easyhz.patchnote.data.mapper.sign.toModel
import com.easyhz.patchnote.data.mapper.sign.toRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val teamRemoteDateSource: TeamRemoteDateSource,
): UserRepository {
    override fun isLogin(): Boolean {
        return authDataSource.isLogin()
    }

    override fun getUserId(): String? {
        return authDataSource.getUserId()
    }

    override fun logOut() {
        authDataSource.logOut()
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
        return authDataSource.getUser(uid).map { it.toModel(null) }
    }

    override suspend fun deleteUserFromLocal() {
        userLocalDataSource.deleteUser()
    }

    override suspend fun deleteUserFromRemote(uid: String): Result<Unit> {
        return authDataSource.deleteUser(uid)
    }

    override suspend fun updateUserFromRemote(): Result<Unit> = runCatching {
        val uid = authDataSource.getUserId() ?: return Result.failure(Exception("User not found"))
        val currentTeamId = userLocalDataSource.getCurrentTeamId().getOrNull()
        val user = authDataSource.getUser(uid).getOrThrow()
        val teamId = if(!currentTeamId.isNullOrBlank() && currentTeamId in user.teamIds) {
            val teamName = teamRemoteDateSource.findTeamById(currentTeamId).getOrThrow()
            userLocalDataSource.updateTeamName(teamName.name)
            currentTeamId
        } else {
            null
        }

        userLocalDataSource.updateUser(user.toModel(teamId))
    }

    override suspend fun setIsFirstOpen(isFirstOpen: Boolean) {
        return userLocalDataSource.setIsFirstOpen(isFirstOpen)
    }

    override suspend fun isFirstOpen(): Result<Boolean> {
        return userLocalDataSource.isFirstOpen()
    }

    override suspend fun deleteTeam(userId: String): Result<Unit> = runCatching {
        userLocalDataSource.deleteTeamName()
        userLocalDataSource.deleteUser()
        authDataSource.deleteTeamId(userId)
    }

    override suspend fun updateTeamName(teamName: String): Result<Unit> = runCatching {
        userLocalDataSource.updateTeamName(teamName)
    }

    override suspend fun isOfflineFirstOpen(): Result<Boolean> {
        return userLocalDataSource.isOfflineFirstOpen()
    }

    override suspend fun setIsOfflineFirstOpen(newValue: Boolean) {
        userLocalDataSource.setIsOfflineFirstOpen(newValue)
    }

    override suspend fun saveTeamFromLocal(teamId: String, teamName: String): Result<Unit> = runCatching {
        userLocalDataSource.setCurrentTeamId(teamId)
        userLocalDataSource.updateTeamName(teamName)
    }
}