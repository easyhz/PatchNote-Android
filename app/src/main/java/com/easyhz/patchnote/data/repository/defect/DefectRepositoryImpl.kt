package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.data.datasource.remote.defect.DefectDataSource
import com.easyhz.patchnote.data.mapper.defect.toData
import com.easyhz.patchnote.data.mapper.defect.toModel
import javax.inject.Inject

class DefectRepositoryImpl @Inject constructor(
    private val defectDataSource: DefectDataSource
): DefectRepository {
    override suspend fun createDefect(param: EntryDefect): Result<Unit> {
        return defectDataSource.createDefect(param.toData())
    }

    override suspend fun fetchDefects(filterParam: FilterParam): Result<List<DefectItem>> {
        val searchFieldParam = filterParam.searchFieldParam.entries.joinToString("||") {
            "${it.key}=${it.value}"
        }
        val indexSearchField = filterParam.toIndexField()
        return defectDataSource.fetchDefects(searchFieldParam, indexSearchField).map { it.map { defectData -> defectData.toModel() } }
    }

    override suspend fun fetchDefect(id: String): Result<DefectItem> {
        return defectDataSource.fetchDefect(id).map { it.toModel() }
    }

    override suspend fun updateDefectCompletion(param: DefectCompletion): Result<Unit> {
        return defectDataSource.updateDefectCompletion(param.id, param.toData())
    }

    override suspend fun deleteDefect(id: String): Result<Unit> {
        return defectDataSource.deleteDefect(id)
    }
}