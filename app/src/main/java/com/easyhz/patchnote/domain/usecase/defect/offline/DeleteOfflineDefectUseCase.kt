package com.easyhz.patchnote.domain.usecase.defect.offline

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import javax.inject.Inject

class DeleteOfflineDefectUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return defectRepository.deleteOfflineDefect(param)
    }
}