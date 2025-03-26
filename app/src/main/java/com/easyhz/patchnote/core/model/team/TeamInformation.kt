package com.easyhz.patchnote.core.model.team

data class TeamInformation(
    val team: Team,
    val adminName: String,
) {
    companion object {
        val Empty = TeamInformation(
            team = Team.Empty,
            adminName = "",
        )
    }
}
