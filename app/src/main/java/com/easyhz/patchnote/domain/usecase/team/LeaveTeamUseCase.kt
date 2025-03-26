package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class LeaveTeamUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> = runCatching {
        val uid = userRepository.getUserId() ?: throw AppError.NoUserDataError
        userRepository.leaveTeam(uid = uid, teamId = param)
    }

}