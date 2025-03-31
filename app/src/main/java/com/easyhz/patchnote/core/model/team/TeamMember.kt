package com.easyhz.patchnote.core.model.team

import com.easyhz.patchnote.core.model.user.TeamJoinDate
import java.time.LocalDateTime

data class TeamMember(
    val id: String,
    val name: String,
    val phone: String,
    val currentTeamId: String?,
    val teamIds: List<String>,
    val teamJoinDates: List<TeamJoinDate>,
    val creationTime: LocalDateTime,
    val isAdmin: Boolean,
) {
    companion object {
        val Empty = TeamMember(
            id = "",
            name = "",
            phone = "",
            currentTeamId = "",
            teamIds = emptyList(),
            teamJoinDates = emptyList(),
            creationTime = LocalDateTime.now(),
            isAdmin = false
        )
    }
}
