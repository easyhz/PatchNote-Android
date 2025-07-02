package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.team.JoinTeamParam
import com.easyhz.patchnote.core.model.team.TeamRole
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class JoinTeamUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
): BaseUseCase<JoinTeamParam, Unit>() {
    override suspend fun invoke(param: JoinTeamParam): Result<Unit> = runCatching {
        val user = param.user.copy(teams = param.user.teams + param.team)
        teamRepository.joinTeam(teamId = param.team.id, userId = param.user.id, role = TeamRole.MEMBER)
        userRepository.updateUser(user)
    }
}