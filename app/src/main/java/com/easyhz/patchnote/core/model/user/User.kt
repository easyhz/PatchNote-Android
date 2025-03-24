package com.easyhz.patchnote.core.model.user

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val currentTeamId: String?,
    val teamIds: List<String>,
    val teamJoinDates: List<TeamJoinDate> = emptyList()
) {
    companion object {
        val Empty = User(
            id = "",
            name = "",
            phone = "",
            currentTeamId = "",
            teamIds = emptyList()
        )
    }
}
