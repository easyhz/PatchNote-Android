package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.data.model.defect.data.DefectCompletionData
import com.easyhz.patchnote.data.model.defect.data.DefectData

interface DefectDataSource {
    suspend fun createDefect(data: DefectData): Result<Unit>
    suspend fun fetchDefects(search: String?): Result<List<DefectData>>
    suspend fun fetchDefect(id: String): Result<DefectData>
    suspend fun updateDefectCompletion(id: String, data: DefectCompletionData): Result<Unit>
}