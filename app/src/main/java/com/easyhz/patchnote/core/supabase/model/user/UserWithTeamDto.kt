package com.easyhz.patchnote.core.supabase.model.user

import com.easyhz.patchnote.core.supabase.constant.Table
import com.easyhz.patchnote.core.supabase.model.team.TeamDto
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserWithTeamDto(
    @SerialName(Table.Users.ID)
    val id: String,
    @SerialName(Table.Users.NAME)
    val name: String,
    @SerialName(Table.Users.PHONE)
    val phone: String,
    val teamList: List<TeamDto>,
    @SerialName(Table.Users.CREATED_AT)
    val createdAt: Instant
)


@Serializable
data class UserWithUserTeamMapDto(
    @SerialName(Table.Users.ID)
    val id: String,
    @SerialName(Table.Users.NAME)
    val name: String,
    @SerialName(Table.Users.PHONE)
    val phone: String,
    @SerialName(Table.Users.CREATED_AT)
    val createdAt: Instant,
    @SerialName(Table.UserTeamMap.DTO_NAME)
    val userTeamMap: List<UserTeamMapDto>
)

@Serializable
data class UserTeamMapDto(
    @SerialName(Table.Teams.DTO_NAME)
    val team: TeamDto
)