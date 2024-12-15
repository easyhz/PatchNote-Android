package com.easyhz.patchnote.data.datasource.remote.team

import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.model.team.response.TeamResponse

interface TeamRemoteDateSource {
    suspend fun findTeamByCode(inviteCode: String): Result<TeamResponse>
    suspend fun createTeam(team: Team): Result<Unit>
}