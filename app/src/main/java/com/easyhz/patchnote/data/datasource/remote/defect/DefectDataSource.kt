package com.easyhz.patchnote.data.datasource.remote.defect

import com.easyhz.patchnote.core.model.filter.IndexField
import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.easyhz.patchnote.data.model.defect.data.DefectData

interface DefectDataSource {
    suspend fun createDefect(data: DefectData): Result<Unit>
    suspend fun fetchDefects(search: String?, index: IndexField): Result<List<DefectData>>
    suspend fun fetchDefect(id: String): Result<DefectData>
    suspend fun updateDefectCompletion(id: String, data: DefectCompletionData): Result<Unit>
    suspend fun deleteDefect(id: String): Result<Unit>
}