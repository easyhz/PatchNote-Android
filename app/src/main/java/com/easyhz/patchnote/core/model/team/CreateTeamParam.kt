package com.easyhz.patchnote.core.model.team

import com.easyhz.patchnote.core.model.user.User

data class CreateTeamParam(
    val user: User,
    val team: Team
)
