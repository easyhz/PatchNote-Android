package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.DefectCompletion
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.EntryDefect
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.user.User
import java.io.File

interface DefectRepository {
    suspend fun createDefect(param: EntryDefect): Result<Unit>
    suspend fun fetchDefects(filterParam: FilterParam, user: User): Result<List<DefectItem>>
    suspend fun fetchDefect(id: String): Result<DefectItem>
    suspend fun updateDefectCompletion(param: DefectCompletion): Result<Unit>
    suspend fun deleteDefect(id: String): Result<Unit>
    suspend fun exportDefects(defects: List<DefectItem>): Result<File>
}