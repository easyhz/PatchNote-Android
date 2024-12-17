package com.easyhz.patchnote.data.datasource.remote.team

import com.easyhz.patchnote.data.model.team.request.TeamRequest
import com.easyhz.patchnote.data.model.team.response.TeamResponse

interface TeamRemoteDateSource {
    suspend fun findTeamByCode(inviteCode: String): Result<TeamResponse>
    suspend fun createTeam(team: TeamRequest): Result<Unit>
    suspend fun findTeamById(teamId: String): Result<TeamResponse>
}