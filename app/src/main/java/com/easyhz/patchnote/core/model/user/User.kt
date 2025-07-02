package com.easyhz.patchnote.core.model.user

import com.easyhz.patchnote.core.model.team.Team
import java.time.LocalDateTime

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val currentTeamId: String?,
    val createdAt: LocalDateTime,
    val teams: List<Team>,
) {
    companion object {
        val Empty = User(
            id = "",
            name = "",
            phone = "",
            currentTeamId = "",
            teams = emptyList(),
            createdAt = LocalDateTime.now()
        )
    }
}
