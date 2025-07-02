package com.easyhz.patchnote.data.repository.team

import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.model.team.TeamRole
import com.easyhz.patchnote.core.model.user.User
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun findTeamByCode(inviteCode: String): Result<Team?>
    suspend fun createTeam(team: Team): Result<Unit>
    suspend fun findTeamById(teamId: String): Result<Team?>
    suspend fun getTeamName(): Flow<String>
    suspend fun fetchTeamMembers(teamId: String): Result<List<User>>
    suspend fun joinTeam(teamId: String, userId: String, role: TeamRole): Result<Unit>
}