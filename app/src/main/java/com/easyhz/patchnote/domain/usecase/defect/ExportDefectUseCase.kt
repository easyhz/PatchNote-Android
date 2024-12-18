package com.easyhz.patchnote.domain.usecase.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import java.io.File
import javax.inject.Inject

class ExportDefectUseCase @Inject constructor(
    private val defectRepository: DefectRepository
): BaseUseCase<List<DefectItem>, File>() {
    override suspend fun invoke(param: List<DefectItem>): Result<File> {
        return defectRepository.exportDefects(param)
    }
}