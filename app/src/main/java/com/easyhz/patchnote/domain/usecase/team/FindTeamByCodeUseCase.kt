package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.data.repository.team.TeamRepository
import javax.inject.Inject

class FindTeamByCodeUseCase @Inject constructor(
    private val teamRepository: TeamRepository
): BaseUseCase<String, Team?>() {
    override suspend fun invoke(param: String): Result<Team?> {
        return teamRepository.findTeamByCode(param)
    }
}