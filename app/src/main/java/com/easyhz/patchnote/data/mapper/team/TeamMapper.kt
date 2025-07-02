package com.easyhz.patchnote.data.mapper.team

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.supabase.model.team.TeamDto
import com.easyhz.patchnote.data.model.team.request.TeamRequest
import com.easyhz.patchnote.data.model.team.response.TeamResponse
import java.time.LocalDateTime

internal fun TeamResponse.toModel() = Team(
    id = id,
    name = name,
    adminId = adminId,
    inviteCode = inviteCode,
    creationTime = DateFormatUtil.formatTimestampToDateTime(creationTime) ?: LocalDateTime.now()
)

internal fun Team.toRequest() = TeamRequest(
    id = id,
    name = name,
    adminId = adminId,
    inviteCode = inviteCode
)

internal fun TeamDto.toModel() = Team(
    id = id,
    name = name,
    inviteCode = inviteCode,
    adminId = "",  // TODO 고치기
    creationTime =  LocalDateTime.now() // TODO 고치기
)