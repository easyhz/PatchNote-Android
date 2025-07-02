package com.easyhz.patchnote.data.repository.team

import com.easyhz.patchnote.core.common.util.Generate
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.model.team.TeamRole
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.core.supabase.model.user_team.UserTeamMapDto
import com.easyhz.patchnote.data.datasource.local.user.UserLocalDataSource
import com.easyhz.patchnote.data.datasource.remote.auth.AuthDataSource
import com.easyhz.patchnote.data.datasource.remote.team.TeamRemoteDateSource
import com.easyhz.patchnote.data.mapper.sign.toModel
import com.easyhz.patchnote.data.mapper.team.toModel
import com.easyhz.patchnote.data.mapper.team.toRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamRemoteDateSource: TeamRemoteDateSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val authDataSource: AuthDataSource,
) : TeamRepository {
    override suspend fun findTeamByCode(inviteCode: String): Result<Team?> = runCatching {
        teamRemoteDateSource.findTeamByCode(inviteCode)?.toModel()
    }

    override suspend fun createTeam(team: Team): Result<Unit> = runCatching {
        teamRemoteDateSource.createTeam(team.toRequest())
    }

    override suspend fun findTeamById(teamId: String): Result<Team?> = runCatching {
        teamRemoteDateSource.findTeamById(teamId)?.toModel()
    }

    override suspend fun getTeamName(): Flow<String> =
        userLocalDataSource.getTeamName()

    override suspend fun fetchTeamMembers(teamId: String): Result<List<User>> = runCatching {
        authDataSource.fetchUsers(teamId).map { item -> item.toModel(null) }
    }

    override suspend fun joinTeam(
        teamId: String,
        userId: String,
        role: TeamRole
    ): Result<Unit> = runCatching {
        val dto = UserTeamMapDto(
            id = Generate.randomUUID(),
            teamId = teamId,
            userId = userId,
            role = role.name,
            createdAt = Clock.System.now()
        )
        teamRemoteDateSource.joinTeam(dto)
    }
}