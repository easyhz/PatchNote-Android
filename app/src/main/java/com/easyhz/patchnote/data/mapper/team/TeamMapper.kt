package com.easyhz.patchnote.data.mapper.team

import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.model.team.request.TeamRequest
import com.easyhz.patchnote.data.model.team.response.TeamResponse

internal fun TeamResponse.toModel() = Team(
    id = id,
    name = name,
    adminId = adminId,
    inviteCode = inviteCode
)

internal fun Team.toRequest() = TeamRequest(
    id = id,
    name = name,
    adminId = adminId,
    inviteCode = inviteCode
)