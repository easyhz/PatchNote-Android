package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import javax.inject.Inject

class FetchDefectUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<String, DefectItem>() {

    override suspend fun invoke(param: String): Result<DefectItem> {
        return defectRepository.fetchDefect(param)
    }
}