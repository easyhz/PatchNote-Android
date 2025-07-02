package com.easyhz.patchnote.core.model.team

import java.time.LocalDateTime

data class TeamMember(
    val id: String,
    val name: String,
    val phone: String,
    val currentTeamId: String?,
    val creationTime: LocalDateTime,
    val isAdmin: Boolean,
) {
    companion object {
        val Empty = TeamMember(
            id = "",
            name = "",
            phone = "",
            currentTeamId = "",
            creationTime = LocalDateTime.now(),
            isAdmin = false
        )
    }
}
