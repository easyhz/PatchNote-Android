package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.team.Team
import com.easyhz.patchnote.core.model.user.TeamJoinDate
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FetchTeamsUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository
): BaseUseCase<Unit, List<Team>>() {
    override suspend fun invoke(param: Unit): Result<List<Team>> = runCatching {
        val uid = userRepository.getUserId() ?: throw AppError.NoUserDataError
        val user = userRepository.getUserFromRemote(uid).getOrThrow()
        coroutineScope {
            user.teamJoinDates.sortedWith(
                compareByDescending<TeamJoinDate> { it.joinDate }
                    .thenBy { it.teamId }
            ).map { joinDate ->
                async(dispatcher) {
                    teamRepository.findTeamById(joinDate.teamId).getOrThrow()
                }
            }.awaitAll()
        }
    }
}