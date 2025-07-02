package com.easyhz.patchnote.core.supabase.model.user_team

import com.easyhz.patchnote.core.supabase.constant.Table
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTeamMapDto(
    @SerialName(Table.UserTeamMap.ID)
    val id: String,
    @SerialName(Table.UserTeamMap.USER_ID)
    val userId: String,
    @SerialName(Table.UserTeamMap.TEAM_ID)
    val teamId: String,
    @SerialName(Table.UserTeamMap.ROLE)
    val role: String,
    @SerialName(Table.UserTeamMap.CREATED_AT)
    val createdAt: Instant
)
