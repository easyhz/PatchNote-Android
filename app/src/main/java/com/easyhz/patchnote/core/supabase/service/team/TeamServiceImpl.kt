package com.easyhz.patchnote.core.supabase.service.team

import com.easyhz.patchnote.core.supabase.constant.Table
import com.easyhz.patchnote.core.supabase.model.team.TeamDto
import com.easyhz.patchnote.core.supabase.model.user_team.UserTeamMapDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class TeamServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : TeamService {
    override suspend fun fetchTeamByCode(inviteCode: String): TeamDto? {
        return postgrest.from(Table.Teams.tableName).select {
            filter {
                eq(Table.Teams.INVITE_CODE, inviteCode)
            }
        }.decodeSingleOrNull()
    }

    override suspend fun insertTeam(teamDto: TeamDto) {
        postgrest.from(Table.Teams.tableName).insert(teamDto)
    }

    override suspend fun fetchTeamById(teamId: String): TeamDto? {
        return postgrest.from(Table.Teams.tableName).select {
            filter {
                eq(Table.Teams.ID, teamId)
            }
        }.decodeSingleOrNull()
    }

    override suspend fun insertUserTeamMap(userTeamMapDto: UserTeamMapDto) {
        postgrest.from(Table.UserTeamMap.tableName).insert(userTeamMapDto)
    }

}