package com.easyhz.patchnote.core.supabase.model.user

import com.easyhz.patchnote.core.supabase.constant.Table
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName(Table.Users.ID)
    val id: String,
    @SerialName(Table.Users.NAME)
    val name: String,
    @SerialName(Table.Users.PHONE)
    val phone: String,
    @SerialName(Table.Users.CREATED_AT)
    val createdAt: Instant
)
