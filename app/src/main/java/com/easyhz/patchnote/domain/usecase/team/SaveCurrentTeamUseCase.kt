package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class SaveCurrentTeamUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<Team, Unit>() {
    override suspend fun invoke(param: Team): Result<Unit> {
        return userRepository.saveTeamFromLocal(param.id, param.name)
    }
}