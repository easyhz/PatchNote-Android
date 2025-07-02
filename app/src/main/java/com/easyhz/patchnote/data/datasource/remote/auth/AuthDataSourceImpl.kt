package com.easyhz.patchnote.data.datasource.remote.auth

import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.supabase.model.user.UserDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithTeamDto
import com.easyhz.patchnote.core.supabase.service.auth.AuthService
import com.easyhz.patchnote.core.supabase.service.user.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
//    private val firestore: FirebaseFirestore,
    private val authService: AuthService,
    private val userService: UserService,
): AuthDataSource {
    override fun isLogin(): Boolean = authService.getCurrentUser() != null

    override fun getUserId(): String? = authService.getCurrentUser()?.id

    override suspend fun logOut() = authService.signOut()

    override suspend fun signInWithPhone(phoneNumber: String): Result<Unit> = runCatching {
        return@runCatching authService.signInWithPhone(phone = phoneNumber)
    }

    override suspend fun verifyOTP(
        phoneNumber: String,
        otp: String
    ): Result<Unit> = runCatching {
        return@runCatching authService.verifyOTP(phone = phoneNumber, otp = otp)
    }

    override suspend fun saveUser(userDto: UserDto): Unit {
        userService.insertUser(userDto)
    }

    override suspend fun getUser(uid: String): UserDto? {
        return userService.fetchUser(userId = uid)
    }

    override suspend fun getUserWithTeams(uid: String): UserWithTeamDto? {
        return userService.fetchUserWithTeams(userId = uid)
    }

    override suspend fun deleteUser(uid: String): Unit {

    }

    override suspend fun leaveTeam(uid: String, teamId: String): Unit {

    }

    override suspend fun fetchUsers(teamId: String): List<UserDto>  {
        return emptyList()
    }
}