package com.easyhz.patchnote.core.model.user

import com.easyhz.patchnote.core.model.team.Team

data class UserInformation(
    val user: User,
    val team: Team,
)