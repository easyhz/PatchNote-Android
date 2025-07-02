package com.easyhz.patchnote.core.supabase.service.team

import com.easyhz.patchnote.core.supabase.model.team.TeamDto
import com.easyhz.patchnote.core.supabase.model.user_team.UserTeamMapDto

interface TeamService {
    suspend fun fetchTeamByCode(inviteCode: String): TeamDto?
    suspend fun insertTeam(teamDto: TeamDto)
    suspend fun fetchTeamById(teamId: String): TeamDto?
    suspend fun insertUserTeamMap(userTeamMapDto: UserTeamMapDto)
}