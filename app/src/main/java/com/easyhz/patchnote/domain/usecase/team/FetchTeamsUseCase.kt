package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class FetchTeamsUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<Unit, List<Team>>() {
    override suspend fun invoke(param: Unit): Result<List<Team>> = runCatching {
        val uid = userRepository.getUserId() ?: throw AppError.NoUserDataError
        val user = userRepository.getUserFromRemote(uid).getOrThrow() // TODO 고칠 수 있으면 고치기
        return@runCatching user?.teams ?: emptyList()
    }
}