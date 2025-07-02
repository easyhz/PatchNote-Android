package com.easyhz.patchnote.data.datasource.remote.auth

import com.easyhz.patchnote.core.supabase.model.user.UserDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithTeamDto

interface AuthDataSource {
    fun isLogin(): Boolean
    fun getUserId(): String?
    suspend fun logOut()
    suspend fun signInWithPhone(phoneNumber: String): Result<Unit>
    suspend fun verifyOTP(phoneNumber: String, otp: String): Result<Unit>
    suspend fun saveUser(userDto: UserDto): Unit
    suspend fun getUser(uid: String): UserDto?
    suspend fun getUserWithTeams(uid: String): UserWithTeamDto?
    suspend fun deleteUser(uid: String): Unit
    suspend fun leaveTeam(uid: String, teamId: String): Unit
    suspend fun fetchUsers(teamId: String): List<UserDto>
}