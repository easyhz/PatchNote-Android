package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import javax.inject.Inject

class DeleteDefectUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return defectRepository.deleteDefect(param)
    }
}