package com.easyhz.patchnote.core.supabase.service.user

import com.easyhz.patchnote.core.supabase.model.user.UserDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithTeamDto

interface UserService {
    suspend fun insertUser(userDto: UserDto)
    suspend fun fetchUser(userId: String): UserDto?
    suspend fun fetchUserWithTeams(userId: String): UserWithTeamDto
}