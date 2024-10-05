package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import javax.inject.Inject

class FetchDefectsUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<Unit, List<DefectItem>>() {
    override suspend fun invoke(param: Unit): Result<List<DefectItem>> {
        return defectRepository.fetchDefects()
    }
}