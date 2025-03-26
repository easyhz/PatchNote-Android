package com.easyhz.patchnote.core.model.user

import java.time.LocalDateTime

data class TeamJoinDate(
    val teamId: String,
    val joinDate: LocalDateTime
) {
    companion object {
        fun create(teamId: String): TeamJoinDate {
            return TeamJoinDate(
                teamId = teamId,
                joinDate = LocalDateTime.now()
            )
        }
    }
}
