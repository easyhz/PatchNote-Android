package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.team.TeamInformation
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class FetchTeamUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
): BaseUseCase<Unit, TeamInformation>() {
    override suspend fun invoke(param: Unit): Result<TeamInformation> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        val teamId = user.currentTeamId ?: throw AppError.NoTeamDataError
        val team = teamRepository.findTeamById(teamId).getOrThrow() ?: throw AppError.NoTeamDataError
//        val admin = userRepository.fetchUser(team?.adminId).getOrThrow()
        TeamInformation(team = team, adminName = "") // TODO 고쳐야됨
    }
}