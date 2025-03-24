package com.easyhz.patchnote.domain.usecase.defect.offline

import androidx.paging.PagingData
import com.easyhz.patchnote.core.common.error.AppError
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOfflineDefectsPagingSourceUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defectRepository: DefectRepository
) {
    suspend fun invoke(): Flow<PagingData<DefectItem>> {
        val user = userRepository.getUserFromLocal().getOrElse {
            return flow { throw AppError.NoUserDataError }
        }
        if (user.currentTeamId.isNullOrBlank()) return flow { throw AppError.NoTeamDataError }
        return defectRepository.getOfflineDefectsPagingSource(
            teamId = user.currentTeamId,
            requesterId = user.id,
        )
    }
}