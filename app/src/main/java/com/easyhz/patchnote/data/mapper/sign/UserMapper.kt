package com.easyhz.patchnote.data.mapper.sign

import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.model.user.TeamJoinDate
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.model.sign.common.TeamJoinDateData
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.model.sign.response.UserResponse
import java.time.LocalDateTime

fun User.toRequest() = SaveUserRequest(
    id = id,
    name = name,
    phone = phone,
    teamIds = teamIds,
    teamJoinDates = teamJoinDates.map { it.toRequest() }
)

fun UserResponse.toModel(
    currentTeamId: String?
) = User(
    id = id,
    name = name,
    phone = phone,
    currentTeamId = currentTeamId,
    teamIds = teamIds,
    teamJoinDates = teamJoinDates.map { it.toModel() },
    creationTime = DateFormatUtil.formatTimestampToDateTime(creationTime) ?: LocalDateTime.now()
)

private fun TeamJoinDateData.toModel() = TeamJoinDate(
    teamId = teamId,
    joinDate = DateFormatUtil.formatTimestampToDateTime(joinDate)  ?: LocalDateTime.now(),
)

private fun TeamJoinDate.toRequest() = TeamJoinDateData(
    teamId = teamId,
    joinDate = DateFormatUtil.localDateTimeToTimestamp(joinDate)
)