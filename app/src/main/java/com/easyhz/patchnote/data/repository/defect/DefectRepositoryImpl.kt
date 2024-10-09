package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.data.datasource.defect.DefectDataSource
import com.easyhz.patchnote.data.mapper.defect.toData
import com.easyhz.patchnote.data.mapper.defect.toModel
import javax.inject.Inject

class DefectRepositoryImpl @Inject constructor(
    private val defectDataSource: DefectDataSource
): DefectRepository {
    override suspend fun createDefect(param: EntryDefect): Result<Unit> {
        return defectDataSource.createDefect(param.toData())
    }

    override suspend fun fetchDefects(search: List<String>?): Result<List<DefectItem>> {
        return defectDataSource.fetchDefects(search).map { it.map { defectData -> defectData.toModel() } }
    }
}