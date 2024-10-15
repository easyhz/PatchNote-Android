package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import javax.inject.Inject

class FetchDefectsUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<FilterParam, List<DefectItem>>() {
    override suspend fun invoke(param: FilterParam): Result<List<DefectItem>> {
        return defectRepository.fetchDefects(param)
    }
}