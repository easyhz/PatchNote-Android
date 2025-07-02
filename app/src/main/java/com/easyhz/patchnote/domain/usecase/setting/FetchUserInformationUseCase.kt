package com.easyhz.patchnote.domain.usecase.setting

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.user.UserInformation
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class FetchUserInformationUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
):BaseUseCase<Unit, UserInformation>() {
    override suspend fun invoke(param: Unit): Result<UserInformation> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        val team = teamRepository.findTeamById(user.currentTeamId!!).getOrThrow() ?: throw AppError.NoTeamDataError

        UserInformation(user, team)
    }
}