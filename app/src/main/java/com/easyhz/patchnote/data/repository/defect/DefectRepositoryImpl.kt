package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.data.datasource.defect.DefectDataSource
import com.easyhz.patchnote.data.mapper.defect.toData
import javax.inject.Inject

class DefectRepositoryImpl @Inject constructor(
    private val defectDataSource: DefectDataSource
): DefectRepository {
    override suspend fun createDefect(param: EntryDefect): Result<Unit> {
        return defectDataSource.createDefect(param.toData())
    }
}