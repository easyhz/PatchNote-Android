package com.easyhz.patchnote.data.mapper.sign

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.supabase.model.user.UserDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithTeamDto
import com.easyhz.patchnote.data.mapper.team.toModel
import kotlinx.datetime.Clock.System
import java.time.LocalDateTime

fun User.toDto() = UserDto(
    id = id,
    name = name,
    phone = phone,
    createdAt = System.now(),
)

fun UserDto.toModel(
    currentTeamId: String?
) = User(
    id = id,
    name = name,
    phone = phone,
    currentTeamId = currentTeamId,
    teams = emptyList(),
    createdAt = LocalDateTime.now() // TODO 고치기
)

fun UserWithTeamDto.toModel(
    currentTeamId: String?
) = User(
    id = id,
    name = name,
    phone = phone,
    currentTeamId = currentTeamId,
    teams = teamList.map { it.toModel() },
    createdAt = LocalDateTime.now(), // TODO 고치기
)