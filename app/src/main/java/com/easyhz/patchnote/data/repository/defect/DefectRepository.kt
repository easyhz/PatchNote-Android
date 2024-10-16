package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.filter.FilterParam

interface DefectRepository {
    suspend fun createDefect(param: EntryDefect): Result<Unit>
    suspend fun fetchDefects(filterParam: FilterParam): Result<List<DefectItem>>
    suspend fun fetchDefect(id: String): Result<DefectItem>
    suspend fun updateDefectCompletion(param: DefectCompletion): Result<Unit>
}