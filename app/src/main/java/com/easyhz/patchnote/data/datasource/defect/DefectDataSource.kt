package com.easyhz.patchnote.data.datasource.defect

import com.easyhz.patchnote.data.model.defect.data.DefectData

interface DefectDataSource {
    suspend fun createDefect(data: DefectData): Result<Unit>
    suspend fun fetchDefects(search: List<String>?): Result<List<DefectData>>
}