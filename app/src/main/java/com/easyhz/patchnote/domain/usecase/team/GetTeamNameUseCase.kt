package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.data.repository.team.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamNameUseCase @Inject constructor(
    private val teamRepository: TeamRepository
) {
    suspend operator fun invoke(): Flow<String> {
        return teamRepository.getTeamName()
    }
}