package com.easyhz.patchnote.core.supabase.model.team

import com.easyhz.patchnote.core.supabase.constant.Table
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    @SerialName(Table.Teams.ID)
    val id: String,
    @SerialName(Table.Teams.NAME)
    val name: String,
    @SerialName(Table.Teams.INVITE_CODE)
    val inviteCode: String,
    @SerialName(Table.Teams.CREATED_AT)
    val createdAt: Instant
)