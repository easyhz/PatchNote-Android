package com.easyhz.patchnote.core.model.user

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val teamIds: List<String>,
    val teamJoinDates: List<TeamJoinDate> = emptyList()
) {
    companion object {
        val Empty = User(
            id = "",
            name = "",
            phone = "",
            teamIds = emptyList()
        )
    }
}
