package com.easyhz.patchnote.domain.usecase.team

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.repository.team.TeamRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FetchTeamMemberUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
): BaseUseCase<Unit, List<User>>() {
    override suspend fun invoke(param: Unit): Result<List<User>> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        val teamId = user.currentTeamId ?: throw AppError.NoTeamDataError
        coroutineScope {
            val membersDeferred = async { teamRepository.fetchTeamMembers(teamId) }
            val adminDeferred = async { teamRepository.findTeamById(teamId) }

            val members = membersDeferred.await().getOrThrow().toMutableList()
            val admin = adminDeferred.await().getOrThrow()

            val (adminList, others) = members.partition { it.id == admin.adminId }
            if (adminList.isEmpty()) throw AppError.UnexpectedError
            adminList + others
        }
    }
}