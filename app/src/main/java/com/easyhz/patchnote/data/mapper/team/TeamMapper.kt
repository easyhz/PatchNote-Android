package com.easyhz.patchnote.data.mapper.team

import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.model.team.response.TeamResponse

internal fun TeamResponse.toModel() = Team(
    id = id,
    name = name,
    adminId = adminId,
    inviteCode = inviteCode
)