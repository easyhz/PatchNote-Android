package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.team.TeamMember
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FetchTeamMemberUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
): BaseUseCase<Unit, List<TeamMember>>() {
    override suspend fun invoke(param: Unit): Result<List<TeamMember>> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        val teamId = user.currentTeamId ?: throw AppError.NoTeamDataError
        coroutineScope {
            val membersDeferred = async { teamRepository.fetchTeamMembers(teamId) }
            val teamDeferred = async { teamRepository.findTeamById(teamId) }

            val members = membersDeferred.await().getOrThrow()
            val team = teamDeferred.await().getOrThrow()

            members.map {
                it.toTeamUser(it.id == team.adminId)
            }.sortedWith(compareByDescending<TeamMember> { it.isAdmin }.thenBy { it.name })
        }
    }

    private fun User.toTeamUser(isAdmin: Boolean): TeamMember {
        return TeamMember(
            id = id,
            name = name,
            phone = phone,
            currentTeamId = currentTeamId,
            teamIds = teamIds,
            teamJoinDates = teamJoinDates,
            creationTime = creationTime,
            isAdmin = isAdmin
        )
    }
}