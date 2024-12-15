package com.easyhz.patchnote.data.repository.team

import com.easyhz.patchnote.core.model.team.Team

interface TeamRepository {
    suspend fun findTeamByCode(inviteCode: String): Result<Team>
    suspend fun createTeam(team: Team): Result<Unit>
}