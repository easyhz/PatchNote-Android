package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.team.CreateTeamParam
import com.easyhz.patchnote.core.model.team.TeamRole
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CreateTeamUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository
): BaseUseCase<CreateTeamParam, Unit>() {
    override suspend fun invoke(param: CreateTeamParam): Result<Unit> = coroutineScope {
        runCatching {
            val user = param.user.copy(teams = param.user.teams + param.team)
            userRepository.updateUser(user)
            teamRepository.createTeam(param.team).getOrThrow()
            teamRepository.joinTeam(teamId = param.team.id, userId = param.user.id, role = TeamRole.ADMIN).getOrThrow()
        }
    }

}