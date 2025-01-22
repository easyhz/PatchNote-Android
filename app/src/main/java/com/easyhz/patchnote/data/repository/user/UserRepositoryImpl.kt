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
        return authDataSource.getUser(uid).map { it.toModel() }
    }

    override suspend fun deleteUserFromLocal() {
        userLocalDataSource.deleteUser()
    }

    override suspend fun deleteUserFromRemote(uid: String): Result<Unit> {
        return authDataSource.deleteUser(uid)
    }

    override suspend fun updateUserFromRemote(): Result<Unit> = runCatching {
        val uid = authDataSource.getUserId() ?: return Result.failure(Exception("User not found"))
        val user = authDataSource.getUser(uid).getOrThrow()
        val teamName = teamRemoteDateSource.findTeamById(user.teamId).getOrThrow()

        userLocalDataSource.updateUser(user.toModel())
        userLocalDataSource.updateTeamName(teamName.name)
    }

    override suspend fun setIsFirstOpen(isFirstOpen: Boolean) {
        return userLocalDataSource.setIsFirstOpen(isFirstOpen)
    }

    override suspend fun isFirstOpen(): Result<Boolean> {
        return userLocalDataSource.isFirstOpen()
    }
}