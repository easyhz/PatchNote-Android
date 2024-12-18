package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.team.CreateTeamParam
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CreateTeamUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository
): BaseUseCase<CreateTeamParam, Unit>() {
    override suspend fun invoke(param: CreateTeamParam): Result<Unit> = coroutineScope {
        runCatching {
            userRepository.updateUser(param.user)
            val userJob = async { userRepository.saveUser(param.user).getOrThrow() }
            val teamJob = async { teamRepository.createTeam(param.team).getOrThrow() }

            userJob.await()
            teamJob.await()
        }
    }

}